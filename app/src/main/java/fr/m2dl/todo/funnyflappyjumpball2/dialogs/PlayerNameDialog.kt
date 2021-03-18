package fr.m2dl.todo.funnyflappyjumpball2.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import fr.m2dl.todo.funnyflappyjumpball2.R

class PlayerNameDialog: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity!!.let {
            val inflater = requireActivity().layoutInflater

            AlertDialog.Builder(it).run {
                setView(inflater.inflate(R.layout.player_name_dialog_fragment, null))
                setPositiveButton(R.string.ok) { dialog, id ->
                    dialog.cancel()
                }
                setNegativeButton(R.string.cancel) { dialog, id ->
                    dialog.cancel()
                }
                create()
            }
        }
    }
}
