/*
 * This file is generated by jOOQ.
 */
package cs499.data_classes.tables.records;


import cs499.data_classes.tables.QuizQuestions;

import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class QuizQuestionsRecord extends UpdatableRecordImpl<QuizQuestionsRecord> implements Record2<Integer, Integer> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>quiz_questions.quiz_id</code>.
     */
    public void setQuizId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>quiz_questions.quiz_id</code>.
     */
    public Integer getQuizId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>quiz_questions.question_id</code>.
     */
    public void setQuestionId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>quiz_questions.question_id</code>.
     */
    public Integer getQuestionId() {
        return (Integer) get(1);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record2<Integer, Integer> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row2<Integer, Integer> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    @Override
    public Row2<Integer, Integer> valuesRow() {
        return (Row2) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return QuizQuestions.QUIZ_QUESTIONS.QUIZ_ID;
    }

    @Override
    public Field<Integer> field2() {
        return QuizQuestions.QUIZ_QUESTIONS.QUESTION_ID;
    }

    @Override
    public Integer component1() {
        return getQuizId();
    }

    @Override
    public Integer component2() {
        return getQuestionId();
    }

    @Override
    public Integer value1() {
        return getQuizId();
    }

    @Override
    public Integer value2() {
        return getQuestionId();
    }

    @Override
    public QuizQuestionsRecord value1(Integer value) {
        setQuizId(value);
        return this;
    }

    @Override
    public QuizQuestionsRecord value2(Integer value) {
        setQuestionId(value);
        return this;
    }

    @Override
    public QuizQuestionsRecord values(Integer value1, Integer value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached QuizQuestionsRecord
     */
    public QuizQuestionsRecord() {
        super(QuizQuestions.QUIZ_QUESTIONS);
    }

    /**
     * Create a detached, initialised QuizQuestionsRecord
     */
    public QuizQuestionsRecord(Integer quizId, Integer questionId) {
        super(QuizQuestions.QUIZ_QUESTIONS);

        setQuizId(quizId);
        setQuestionId(questionId);
    }
}