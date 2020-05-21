package co.zsmb.rainbowcake.channels.livedata

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel

@Deprecated(
        "Channel support is being removed",
        level = DeprecationLevel.WARNING
)
@UseExperimental(ExperimentalCoroutinesApi::class)
internal class ChannelLifecycleOwner(
        channel: Channel<*>
) : LifecycleOwner {

    private val lifecycle = LifecycleRegistry(this)

    init {
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_START)
        channel.invokeOnClose {
            lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
        }
    }

    override fun getLifecycle(): Lifecycle {
        return lifecycle
    }
}
