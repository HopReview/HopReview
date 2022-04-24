package com.example.teame_hopreview.ui.review;

import com.example.teame_hopreview.database.Professor;

public class ProfessorWrapper {
    private Professor professor;

    public ProfessorWrapper(com.example.teame_hopreview.database.Professor professor) {
        this.professor = professor;
    }

    public com.example.teame_hopreview.database.Professor getProfessor() {
        return professor;
    }

    public void setCourse(com.example.teame_hopreview.database.Professor professor) {
        this.professor = professor;
    }

    @Override
    public String toString() {
        return professor.getName();
    }
}
