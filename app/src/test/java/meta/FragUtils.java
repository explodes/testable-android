package meta;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.LinearLayout;

import org.robolectric.Robolectric;
import org.robolectric.android.controller.ActivityController;

import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startVisibleFragment;

public final class FragUtils {

	private FragUtils() {
	}

	@NonNull
	public static <F extends Fragment> F resumeFragment(@NonNull F frag) {
		startVisibleFragment(frag);
		frag.getActivity().getSupportFragmentManager().executePendingTransactions();
		return frag;
	}

	@NonNull
	public static <F extends Fragment> F pauseFragment(@NonNull F frag) {
		return moveFragmentToLifecycleEvent(frag, ActivityController::pause);
	}

	@NonNull
	public static <F extends Fragment> F stopFragment(@NonNull F frag) {
		return moveFragmentToLifecycleEvent(frag, ctrl -> ctrl.pause().stop());
	}

	@NonNull
	public static <F extends Fragment> F destroyFragment(@NonNull F frag) {
		return moveFragmentToLifecycleEvent(frag, ctrl -> ctrl.pause().stop().destroy());
	}

	@NonNull
	private static <F extends Fragment> F moveFragmentToLifecycleEvent(@NonNull F frag, @NonNull ControllerAction ctrl) {
		ActivityController controller = Robolectric.buildActivity(FragmentUtilActivity.class).create().start().postCreate(null).resume().visible();
		FragmentUtilActivity activity = (FragmentUtilActivity) controller.get();

		activity.getSupportFragmentManager()
			.beginTransaction().add(FragmentUtilActivity.VIEW_ID, frag, null).commit();

		activity.getSupportFragmentManager().executePendingTransactions();

		ctrl.call(controller);

		return frag;
	}

	@FunctionalInterface
	private interface ControllerAction {
		void call(@NonNull ActivityController ctrl);
	}

	private static class FragmentUtilActivity extends FragmentActivity {

		@IdRes
		private static final int VIEW_ID = 1;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			LinearLayout view = new LinearLayout(this);
			view.setId(VIEW_ID);
			setContentView(view);
		}
	}
}
