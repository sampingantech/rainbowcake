package co.zsmb.rainbowcake.koin

import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import co.zsmb.rainbowcake.base.ViewModelScope
import co.zsmb.rainbowcake.base.ViewModelScope.Activity
import co.zsmb.rainbowcake.base.ViewModelScope.Default
import co.zsmb.rainbowcake.base.ViewModelScope.ParentFragment
import org.koin.android.viewmodel.ext.android.getViewModel
import org.koin.core.qualifier.named

/**
 * Uses the global Koin instance to fetch the appropriate ViewModel instance for the Fragment.
 *
 * @param scope The scope that the ViewModel should be fetched from and exist in.
 *              See [ViewModelScope] for details.
 */
inline fun <F : RainbowCakeFragment<VS, VM>, VS, reified VM : RainbowCakeViewModel<VS>> F.getViewModelFromFactory(
        scope: ViewModelScope = Default
): VM {
    return when (scope) {
        Default -> {
            this.getViewModel()
        }
        is ParentFragment -> {
            val parentFragment = getParentFragment()
                    ?: throw IllegalStateException("No parent Fragment")
            val key = scope.key
            if (key != null) {
                parentFragment.getViewModel(named(key))
            } else {
                parentFragment.getViewModel()
            }
        }
        is Activity -> {
            val key = scope.key
            if (key != null) {
                requireActivity().getViewModel(named(key))
            } else {
                requireActivity().getViewModel()
            }
        }
    }
}
