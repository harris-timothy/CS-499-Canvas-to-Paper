package cs499;

import cs499.qti.CreateQTI;
import cs499.qti.ParseQTI;
import cs499.question.Question;
import cs499.question.QuestionFactory;
import cs499.question.SingleAnswerQuestion;
import cs499.screens.WelcomeScreen;
import jakarta.xml.bind.JAXBException;

import static cs499.data_classes.Tables.QUESTION;
import static cs499.data_classes.Tables.QUIZ;
import static cs499.data_classes.Tables.QUESTION_BANK;
import static cs499.data_classes.Tables.QUESTION_BANK_QUESTION;
import static cs499.data_classes.Tables.QUESTION_GROUP;
import static cs499.data_classes.Tables.QUIZ_TO_QUESTION;


import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Scanner;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;


public class Main {

	static Scanner cin = new Scanner(System.in);

	public static void main(String[] args) {
		
//		QuizBuilder builder = new QuizBuilder();
//		
//		ArrayList<Quiz> testquiz = new ArrayList<Quiz>();
//		testquiz.add(builder.buildQuiz(1));
//		testquiz.add(builder.buildQuiz(4));
//		
//		CreateQTI qti = new CreateQTI();
//		try {
//			qti.createPackage(testquiz,"c:/users/black/desktop");
//		} catch (JAXBException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
		String testpath = "D:\\black\\Documents\\GitHub\\CS-499-Canvas-to-Paper\\Canvas to Paper\\qti";
		ParseQTI parser = new ParseQTI();
		
		try {
			parser.xmlLoop(testpath);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		
//		try (Connection conn = DriverManager.getConnection(DataHelper.ENV.get("DB_URL"))) {
//			DSLContext create = DSL.using(conn, SQLDialect.SQLITE);
//			
//			create.truncate(QUESTION).execute();
//			create.truncate(QUIZ).execute();
//			create.truncate(QUESTION_GROUP).execute();
//			create.truncate(QUIZ_TO_QUESTION).execute();
//			create.truncate(QUESTION_BANK).execute();
//			create.truncate(QUESTION_BANK_QUESTION).execute();
//			
//			
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//		
//		

//		new WelcomeScreen();
//
//		int correct = 0;
//
//		int incorrect = 0;
//
//		int questions = 5;
//
//		System.out.println("Test templete\n");
//
//		String[][] Ques_Ans = {
//
//				{ "A statement is a declarative sentence that is either true or false  ", "\n T.\n F. \n", "F" },
//
//				{ "The unary connective not which uses the symbols ' or ~, negates the value of the proposition.  ",
//						"\n T.\n F \n", "T" },
//
//				{ "In the argument P1 ^ P2 &^ P3 ^ ... ^Pn --> Q the Px's are referred to as the hypotheses.  ",
//						"\n T.\n F. \n", "T" },
//
//				{ "(A ^ B)' <=> A' v B' is an example of DeBoolean's law.  ", "\n T.\n F \n", "F" },
//
//				{ "A statement is a declarative sentence that is either true or false ", "\n T.\n F \n", "T" } };
//
//		String[] user_ans = new String[(int) questions];
//
//		int i = 0;
//
//		do {
//
//			System.out.print("" + (i + 1) + ". " + Ques_Ans[i][0] + " " + Ques_Ans[i][1]);
//
//			user_ans[i] = String.valueOf(cin.next().charAt(0));
//
//			isValid(user_ans);
//
//			if (Ques_Ans[i][2].equals(user_ans[i])) {
//
//				System.out.println("\n Correct!");
//
//				correct++;
//
//			}
//
//			else
//
//			{
//
//				System.out.println("\n Incorrect. The correct answer is " + Ques_Ans[i][2]);
//
//				incorrect++;
//
//			}
//
//			System.out.print("\n");
//
//			i++;
//
//		} while (i < questions);
//
//		System.out.println("\n Number of correct answers: " + correct);
//
//		System.out.println("\n Number of incorrect answers: " + incorrect);
//
//		System.exit(0);

	}

	private static void isValid(String[] user_ans) {
		// TODO Auto-generated method stub

	}

}
