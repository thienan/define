package in.workarounds.define.portal;

import dagger.Module;
import dagger.Provides;
import in.workarounds.define.base.NotificationUtils;
import in.workarounds.define.helper.ContextHelper;
import in.workarounds.define.ui.view.SelectionCard.SelectionCardController;

/**
 * Created by madki on 26/09/15.
 */
@Module
public class PortalModule {
    private MeaningPortal portal;

    public PortalModule(MeaningPortal portal) {
        this.portal = portal;
    }

    @Provides @PerPortal
    public NotificationUtils provideNotificationUtils() {
        return new NotificationUtils(portal);
    }

    @Provides @PerPortal
    public MeaningsController providesMeaningsController() {
        return portal;
    }

    @Provides @PerPortal
    public SelectionCardController providesSelectionCardListener() {
        return portal;
    }

    @Provides @PerPortal
    public ContextHelper providesContextHelper() {
        return new ContextHelper(portal, true);
    }
}