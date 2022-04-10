package com.example.teame_hopreview.ui.review;

import com.example.teame_hopreview.ui.professors.Professor;

public class ProfessorWrapper {
    private Professor professor;

    public ProfessorWrapper(Professor professor) {
        this.professor = professor;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setCourse(Professor professor) {
        this.professor = professor;
    }

    @Override
    public String toString() {
        return professor.getProfessorName();
    }
}
