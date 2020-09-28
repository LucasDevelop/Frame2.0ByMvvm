import android.content.Context
import androidx.annotation.CallSuper
import com.luan.base.dialog.LoadingDialog
import com.luan.core.CoreApp
import com.luan.core.custom.dialog.INetLoadingDialog

/**
 * @package
 * @author     luan
 * @date       2020/9/25
 * @des
 */
abstract class BaseApp : CoreApp() {

    @CallSuper
    override fun onInitConfig() {

    }

    override fun createNetDialog(context: Context): INetLoadingDialog {
        return LoadingDialog(context)
    }
}