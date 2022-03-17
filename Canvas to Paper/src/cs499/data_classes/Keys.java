/*
 * This file is generated by jOOQ.
 */
package cs499.data_classes;


import cs499.data_classes.tables.Course;
import cs499.data_classes.tables.Instructor;
import cs499.data_classes.tables.Metadata;
import cs499.data_classes.tables.Question;
import cs499.data_classes.tables.QuestionBank;
import cs499.data_classes.tables.QuestionBankQuestion;
import cs499.data_classes.tables.QuestionGroup;
import cs499.data_classes.tables.Quiz;
import cs499.data_classes.tables.QuizReference;
import cs499.data_classes.tables.QuizToQuestion;
import cs499.data_classes.tables.ReferenceMaterial;
import cs499.data_classes.tables.records.CourseRecord;
import cs499.data_classes.tables.records.InstructorRecord;
import cs499.data_classes.tables.records.MetadataRecord;
import cs499.data_classes.tables.records.QuestionBankQuestionRecord;
import cs499.data_classes.tables.records.QuestionBankRecord;
import cs499.data_classes.tables.records.QuestionGroupRecord;
import cs499.data_classes.tables.records.QuestionRecord;
import cs499.data_classes.tables.records.QuizRecord;
import cs499.data_classes.tables.records.QuizReferenceRecord;
import cs499.data_classes.tables.records.QuizToQuestionRecord;
import cs499.data_classes.tables.records.ReferenceMaterialRecord;

import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables in the
 * default schema.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<CourseRecord> COURSE__ = Internal.createUniqueKey(Course.COURSE, DSL.name(""), new TableField[] { Course.COURSE.ID }, true);
    public static final UniqueKey<InstructorRecord> INSTRUCTOR__ = Internal.createUniqueKey(Instructor.INSTRUCTOR, DSL.name(""), new TableField[] { Instructor.INSTRUCTOR.ID, Instructor.INSTRUCTOR.EMAIL, Instructor.INSTRUCTOR.TOKEN, Instructor.INSTRUCTOR.API_ID }, true);
    public static final UniqueKey<MetadataRecord> METADATA__ = Internal.createUniqueKey(Metadata.METADATA, DSL.name(""), new TableField[] { Metadata.METADATA.ID }, true);
    public static final UniqueKey<QuestionRecord> QUESTION__ = Internal.createUniqueKey(Question.QUESTION, DSL.name(""), new TableField[] { Question.QUESTION.ID }, true);
    public static final UniqueKey<QuestionBankRecord> QUESTION_BANK__ = Internal.createUniqueKey(QuestionBank.QUESTION_BANK, DSL.name(""), new TableField[] { QuestionBank.QUESTION_BANK.ID }, true);
    public static final UniqueKey<QuestionBankQuestionRecord> QUESTION_BANK_QUESTION__ = Internal.createUniqueKey(QuestionBankQuestion.QUESTION_BANK_QUESTION, DSL.name(""), new TableField[] { QuestionBankQuestion.QUESTION_BANK_QUESTION.QUESTION_ID, QuestionBankQuestion.QUESTION_BANK_QUESTION.QUESTION_BANK_ID }, true);
    public static final UniqueKey<QuestionGroupRecord> QUESTION_GROUP__ = Internal.createUniqueKey(QuestionGroup.QUESTION_GROUP, DSL.name(""), new TableField[] { QuestionGroup.QUESTION_GROUP.ID }, true);
    public static final UniqueKey<QuizRecord> QUIZ__ = Internal.createUniqueKey(Quiz.QUIZ, DSL.name(""), new TableField[] { Quiz.QUIZ.ID }, true);
    public static final UniqueKey<QuizReferenceRecord> QUIZ_REFERENCE__ = Internal.createUniqueKey(QuizReference.QUIZ_REFERENCE, DSL.name(""), new TableField[] { QuizReference.QUIZ_REFERENCE.QUIZ_ID, QuizReference.QUIZ_REFERENCE.REFERENCE_ID }, true);
    public static final UniqueKey<QuizToQuestionRecord> QUIZ_TO_QUESTION__ = Internal.createUniqueKey(QuizToQuestion.QUIZ_TO_QUESTION, DSL.name(""), new TableField[] { QuizToQuestion.QUIZ_TO_QUESTION.QUIZ_ID, QuizToQuestion.QUIZ_TO_QUESTION.QUESTION_ID }, true);
    public static final UniqueKey<ReferenceMaterialRecord> REFERENCE_MATERIAL__ = Internal.createUniqueKey(ReferenceMaterial.REFERENCE_MATERIAL, DSL.name(""), new TableField[] { ReferenceMaterial.REFERENCE_MATERIAL.ID, ReferenceMaterial.REFERENCE_MATERIAL.NAME }, true);
}
