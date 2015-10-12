package in.workarounds.define.urban;

import android.text.TextUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import in.workarounds.define.base.Dictionary;
import in.workarounds.define.base.DictionaryException;
import in.workarounds.define.base.Result;
import in.workarounds.define.portal.PerPortal;
import in.workarounds.define.util.LogUtils;
import retrofit.Call;
import retrofit.Response;

/**
 * Created by madki on 26/09/15.
 */
@PerPortal
public class UrbanDictionary implements Dictionary {
    private static final String TAG = LogUtils.makeLogTag(UrbanDictionary.class);
    private final UrbanApi api;

    @Inject
    public UrbanDictionary(UrbanApi api) {
        this.api = api;
    }

    @Override
    public List<Result> results(String word) throws DictionaryException {
        List<Result> results = new ArrayList<>();
        if (!TextUtils.isEmpty(word)) {
            Call<UrbanResult> call = api.define(word);
            UrbanResult urbanResult = null;
            try {
                Response<UrbanResult> response = call.execute();
                urbanResult = response.body();
            } catch (IOException e) {
                throw new DictionaryException(
                        DictionaryException.NETWORK_ERROR,
                        "Unable to fetch data from Urban Dictionary servers. Please check your network connection."
                );
            }
            if (urbanResult != null) {
                for (Meaning meaning : urbanResult.getMeanings()) {
                    results.add(toResult(meaning, urbanResult));
                }
            }
        }
        return results;
    }

    private Result toResult(Meaning meaning, UrbanResult urbanResult) {
        Result result = new Result();
        result.definition(meaning.getDefinition());
        List<String> usages = new ArrayList<>();
        usages.add(meaning.getExample());
        result.usages(usages);
        result.synonyms(urbanResult.getTags());
        result.type(urbanResult.getResultType());
        return result;
    }
}
