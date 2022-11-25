package com.example.citra_moblie.model;

import com.example.citra_moblie.helper.FirebaseHelper;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class Candidate {

    private List<String> candidaturas;

    public void salvar(){
        DatabaseReference candidaturaRef = FirebaseHelper.getDatabaseReference()
                .child("candidate")
                .child(FirebaseHelper.getIdFirebase());
        candidaturaRef.setValue(getCandidaturas());
    }

    public List<String> getCandidaturas() {
        return candidaturas;
    }

    public void setCandidaturas(List<String> candidaturas) {
        this.candidaturas =candidaturas;
    }
}

