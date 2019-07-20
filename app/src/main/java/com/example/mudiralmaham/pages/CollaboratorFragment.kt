package com.example.mudiralmaham.pages

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.mudiralmaham.R
import com.example.mudiralmaham.events.AddCollaborator
import com.example.mudiralmaham.utils.ContextHolder
import com.example.mudiralmaham.webservice.request.AddCollaboratorRequest
import com.example.mudiralmaham.webservice.response.AddResponse
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CollaboratorFragment: DialogFragment() {
    private var _rootView: View? = null
    private var collaboratorEmail: EditText? = null
    private var addCollaborator: Button? = null
    var projectName: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _rootView = inflater.inflate(R.layout.add_collaborator, container, false)
        collaboratorEmail = _rootView?.findViewById(R.id.collaborator_email)
        addCollaborator = _rootView?.findViewById(R.id.add_collaborator_btn)
        return _rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addCollaborator?.setOnClickListener {
            if (!collaboratorEmail?.text.toString().equals("")) {
                val data = AddCollaboratorRequest(collaboratorEmail?.text.toString(), ContextHolder.user?.token, projectName)

                val request: Call<AddResponse> = ContextHolder.webservice.addCollaborator("Bearer ${ContextHolder.user?.token!!}", data)
                request.enqueue(object: Callback<AddResponse> {
                    override fun onFailure(call: Call<AddResponse>, t: Throwable) {
                        Snackbar.make(_rootView!!, "could not send request to collaborator", Snackbar.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<AddResponse>, response: Response<AddResponse>) {
                        if (response.code() == 200){
                            EventBus.getDefault().post(AddCollaborator(collaboratorEmail?.text.toString()))
                        }
                        else {
                            Snackbar.make(_rootView!!, "could not send request to collaborator", Snackbar.LENGTH_SHORT).show()
                        }
                    }
                })

            }
        }
    }
}