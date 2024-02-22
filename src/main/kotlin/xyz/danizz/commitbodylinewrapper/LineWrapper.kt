package xyz.danizz.commitbodylinewrapper

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.event.DocumentEvent
import com.intellij.openapi.editor.event.DocumentListener
import com.intellij.openapi.editor.event.EditorFactoryEvent
import com.intellij.openapi.editor.event.EditorFactoryListener
import com.intellij.openapi.vcs.VcsConfiguration
import com.intellij.openapi.vcs.ui.CommitMessage
import com.intellij.vcs.commit.message.BodyLimitInspection
import com.intellij.vcs.commit.message.CommitMessageInspectionProfile

class LineWrapper : EditorFactoryListener {
    private lateinit var editor: Editor

    override fun editorCreated(event: EditorFactoryEvent) {
        if (event.editor.document.getUserData(CommitMessage.DATA_KEY) == null)
            return
        editor = event.editor
        editor.document.addDocumentListener(DL(editor))
        super.editorCreated(event)
    }

    class DL(private val editor: Editor) : DocumentListener {
        override fun documentChanged(event: DocumentEvent) {
            if (event.document.getLineNumber(event.offset) == 0) {
                editor.settings.setWrapWhenTypingReachesRightMargin(false)
            } else {
                val conf = VcsConfiguration.getInstance(editor.project!!)
                val profile = CommitMessageInspectionProfile.getInstance(editor.project!!)

                editor.settings.setWrapWhenTypingReachesRightMargin(conf.WRAP_WHEN_TYPING_REACHES_RIGHT_MARGIN &&
                        profile.isToolEnabled(BodyLimitInspection::class.java))
            }
            super.documentChanged(event)
        }
    }
}
