package cs499;

import static cs499.data_classes.Tables.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import cs499.question.Question;
import cs499.question.QuestionFactory;

public class QuizBuilder {
	// TODO:
	// Take Quiz ID - DONE
	// Make quiz with Quiz ID - DONE
	// Go to database, find questions that match Quiz ID - QuizToQuestion - DONE
	// It will give list of Question IDs -> may be fucky due to question banks - DONE?
	// Make question objects for all IDs given - QuestionFactory - DONE
	// Quiz object addQuestion method to add question objects to quiz - DONE
	// Add blacklist
	// Add whitelist
	
	// NOTE: May want to change this to one function that deals with key/value pairs in the future.
	// For now, this will work.
	
	public Quiz buildQuiz(int id) {
		Quiz builtQuiz = new Quiz(id);
		QuestionBank bank;
		List<Integer> bankQuestionList;
		
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);     

			// Fetch an array of question IDs from the database that match the given quiz ID
			Integer[] questionArr = create.select(QUIZ_TO_QUESTION.QUESTION_ID)
					.from(QUIZ_TO_QUESTION)
					.where(QUIZ_TO_QUESTION.QUIZ_ID.eq(id))
					.fetchArray(QUIZ_TO_QUESTION.QUESTION_ID);
			
			// Fetch an array of question group records containing the question bank ID and the pick count
			// from the database that match the given quiz ID
			Record[] groupArr = create.select(QUESTION_GROUP.QUESTION_BANK_ID, QUESTION_GROUP.PICK_COUNT)
					.from(QUESTION_GROUP)
					.where(QUESTION_GROUP.QUIZ_ID.eq(id))
					.fetchArray();
			
			// If the array of question IDs is not empty, build Question objects from each ID
			// and add them to the quiz
			if(questionArr != null) { // Maybe change to result.length != 0 if something breaks
				for (Integer questionID : questionArr)
				{
					builtQuiz.addQuestion(QuestionFactory.build(questionID));
				}
			}
			
			// If the array of question group records is not empty:
			// - Randomize the list of questions from the question bank
			// - Choose a number of questions from the question bank based off of PICK_COUNT
			// - Add the chosen questions to the quiz
			if (groupArr != null) {
				for(int i = 0; i < groupArr.length; i++) {
					Integer bankID = groupArr[i].getValue(QUESTION_GROUP.QUESTION_BANK_ID);
					bank = new QuestionBank(bankID);
					bankQuestionList = bank.getQuestionIds();
					Collections.shuffle(bankQuestionList);
					for (int j = 0; j < groupArr[i].getValue(QUESTION_GROUP.PICK_COUNT); j++) {
						builtQuiz.addQuestion(QuestionFactory.build(bankQuestionList.get(j)));
					}
				}
			}
			
			return builtQuiz;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// Blacklist build function
	public Quiz buildQuizBlacklist(int id, List<Integer> blacklist) {
		Quiz builtQuiz = new Quiz(id);
		QuestionBank bank;
		List<Integer> bankQuestionList;
		
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);     

			// Fetch an array of question IDs from the database that match the given quiz ID
			Integer[] questionArr = create.select(QUIZ_TO_QUESTION.QUESTION_ID)
					.from(QUIZ_TO_QUESTION)
					.where(QUIZ_TO_QUESTION.QUIZ_ID.eq(id))
					.fetchArray(QUIZ_TO_QUESTION.QUESTION_ID);
			
			// Fetch an array of question group records containing the question bank ID and the pick count
			// from the database that match the given quiz ID
			Record[] groupArr = create.select(QUESTION_GROUP.QUESTION_BANK_ID, QUESTION_GROUP.PICK_COUNT)
					.from(QUESTION_GROUP)
					.where(QUESTION_GROUP.QUIZ_ID.eq(id))
					.fetchArray();
			
			// If the array of question IDs is not empty, build Question objects from each ID
			// and add them to the quiz
			if(questionArr != null) { // Maybe change to result.length != 0 if something breaks
				for (Integer questionID : questionArr)
				{
					for (int i = 0; i < blacklist.size(); i++)
					{
						if (questionID != blacklist.get(i))
						{
							builtQuiz.addQuestion(QuestionFactory.build(questionID));
						}
						else
						{
							continue; // Unsure if necessary
						}
					}			
				}
			}
			
			// If the array of question group records is not empty:
			// - Randomize the list of questions from the question bank
			// - Choose a number of questions from the question bank based off of PICK_COUNT
			// - Add the chosen questions to the quiz
			if (groupArr != null) {
				int k;
				for(int i = 0; i < groupArr.length; i++) {
					int count = groupArr[i].getValue(QUESTION_GROUP.PICK_COUNT);
					Integer bankID = groupArr[i].getValue(QUESTION_GROUP.QUESTION_BANK_ID);
					bank = new QuestionBank(bankID);
					bankQuestionList = bank.getQuestionIds();
					Collections.shuffle(bankQuestionList);
					for (int j = 0; j < bankQuestionList.size(); j++) {
						for (k = 0; k < blacklist.size(); k++) {
							if (count != 0 && bankQuestionList.get(j) != blacklist.get(k)) {
								builtQuiz.addQuestion(QuestionFactory.build(bankQuestionList.get(j)));
								count--;
							}
						}
						if (k >= blacklist.size() && count != 0) {
							builtQuiz.addQuestion(QuestionFactory.build(bankQuestionList.get(j)));
							count--;
						}
					}
				}
			}
			
			return builtQuiz;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// Whitelist build function
	public Quiz buildQuizWhitelist(int id, List<Integer> whitelist) {
		Quiz builtQuiz = new Quiz(id);
		QuestionBank bank;
		List<Integer> bankQuestionList;
		
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);     

			// Fetch an array of question IDs from the database that match the given quiz ID
			Integer[] questionArr = create.select(QUIZ_TO_QUESTION.QUESTION_ID)
					.from(QUIZ_TO_QUESTION)
					.where(QUIZ_TO_QUESTION.QUIZ_ID.eq(id))
					.fetchArray(QUIZ_TO_QUESTION.QUESTION_ID);
			
			// Fetch an array of question group records containing the question bank ID and the pick count
			// from the database that match the given quiz ID
			Record[] groupArr = create.select(QUESTION_GROUP.QUESTION_BANK_ID, QUESTION_GROUP.PICK_COUNT)
					.from(QUESTION_GROUP)
					.where(QUESTION_GROUP.QUIZ_ID.eq(id))
					.fetchArray();
			
			// If the array of question IDs is not empty, build Question objects from each ID
			// and add them to the quiz
			if(questionArr != null) { // Maybe change to result.length != 0 if something breaks
				for (Integer questionID : questionArr)
				{
					for(int i = 0; i < whitelist.size(); i++)
					{
						if (questionID == whitelist.get(i)) {
							builtQuiz.addQuestion(QuestionFactory.build(questionID));
						}
						else
						{
							continue; // Unsure if necessary
						}
					}
					
				}
			}
			
			// If the array of question group records is not empty:
			// - Randomize the list of questions from the question bank
			// - Choose a number of questions from the question bank based off of PICK_COUNT
			// - Add the chosen questions to the quiz
			if (groupArr != null) {
				int k;
				for(int i = 0; i < groupArr.length; i++) {
					int count = groupArr[i].getValue(QUESTION_GROUP.PICK_COUNT);
					Integer bankID = groupArr[i].getValue(QUESTION_GROUP.QUESTION_BANK_ID);
					bank = new QuestionBank(bankID);
					bankQuestionList = bank.getQuestionIds();
					Collections.shuffle(bankQuestionList);
					for (int j = 0; j < bankQuestionList.size(); j++) {
						for (k = 0; k < whitelist.size(); k++) {
							if (count != 0 && bankQuestionList.get(j) == whitelist.get(k))
							{
								builtQuiz.addQuestion(QuestionFactory.build(bankQuestionList.get(j)));
								count--;
							}
						}
						if (k >= whitelist.size() && count != 0) {
							builtQuiz.addQuestion(QuestionFactory.build(bankQuestionList.get(j)));
							count --;
						}
					}
				}
			}
			
			return builtQuiz;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// Blacklist and Whitelist build function
	public Quiz buildQuizBW(int id, List<Integer> blacklist, List<Integer> whitelist) {
		Quiz builtQuiz = new Quiz(id);
		QuestionBank bank;
		List<Integer> bankQuestionList;
		
		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);     

			// Fetch an array of question IDs from the database that match the given quiz ID
			Integer[] questionArr = create.select(QUIZ_TO_QUESTION.QUESTION_ID)
					.from(QUIZ_TO_QUESTION)
					.where(QUIZ_TO_QUESTION.QUIZ_ID.eq(id))
					.fetchArray(QUIZ_TO_QUESTION.QUESTION_ID);
			
			// Fetch an array of question group records containing the question bank ID and the pick count
			// from the database that match the given quiz ID
			Record[] groupArr = create.select(QUESTION_GROUP.QUESTION_BANK_ID, QUESTION_GROUP.PICK_COUNT)
					.from(QUESTION_GROUP)
					.where(QUESTION_GROUP.QUIZ_ID.eq(id))
					.fetchArray();
			
			// If the array of question IDs is not empty, build Question objects from each ID
			// and add them to the quiz
			if(questionArr != null) { // Maybe change to result.length != 0 if something breaks
				for (Integer questionID : questionArr)
				{
					for(int i = 0; i < whitelist.size(); i++)
					{
						if (questionID == whitelist.get(i)) {
							builtQuiz.addQuestion(QuestionFactory.build(questionID));
						}
						else
						{
							continue; // Unsure if necessary
						}
					}
					
				}
			}
			
			// If the array of question group records is not empty:
			// - Randomize the list of questions from the question bank
			// - Choose a number of questions from the question bank based off of PICK_COUNT
			// - Add the chosen questions to the quiz
			if (groupArr != null) {
				int k;
				for(int i = 0; i < groupArr.length; i++) {
					int count = groupArr[i].getValue(QUESTION_GROUP.PICK_COUNT);
					Integer bankID = groupArr[i].getValue(QUESTION_GROUP.QUESTION_BANK_ID);
					bank = new QuestionBank(bankID);
					bankQuestionList = bank.getQuestionIds();
					Collections.shuffle(bankQuestionList);
					for (int j = 0; j < bankQuestionList.size(); j++) {
						for (k = 0; k < whitelist.size(); k++) {
							if (count != 0 && bankQuestionList.get(j) == whitelist.get(k))
							{
								builtQuiz.addQuestion(QuestionFactory.build(bankQuestionList.get(j)));
								count--;
							}
						}
						for (int h = 0; h < blacklist.size(); h++) {
							if (k >= whitelist.size() && count != 0 && bankQuestionList.get(j) != blacklist.get(h)) {
								builtQuiz.addQuestion(QuestionFactory.build(bankQuestionList.get(j)));
								count --;
							}
						}
					}
				}
			}
			
			return builtQuiz;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
