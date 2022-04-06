package cs499;

import cs499.screens.WelcomeScreen;

import java.util.Scanner;


public class Main {

	static Scanner cin = new Scanner(System.in);

	public static void main(String[] args) {

		new WelcomeScreen();

		int correct = 0;

		int incorrect = 0;

		int questions = 5;

		System.out.println("Test templete\n");

		String[][] Ques_Ans = {

				{ "A statement is a declarative sentence that is either true or false  ", "\n T.\n F. \n", "F" },

				{ "The unary connective not which uses the symbols ' or ~, negates the value of the proposition.  ",
						"\n T.\n F \n", "T" },

				{ "In the argument P1 ^ P2 &^ P3 ^ ... ^Pn --> Q the Px's are referred to as the hypotheses.  ",
						"\n T.\n F. \n", "T" },

				{ "(A ^ B)' <=> A' v B' is an example of DeBoolean's law.  ", "\n T.\n F \n", "F" },

				{ "A statement is a declarative sentence that is either true or false ", "\n T.\n F \n", "T" } };

		String[] user_ans = new String[(int) questions];

		int i = 0;

		do {

			System.out.print("" + (i + 1) + ". " + Ques_Ans[i][0] + " " + Ques_Ans[i][1]);

			user_ans[i] = String.valueOf(cin.next().charAt(0));

			isValid(user_ans);

			if (Ques_Ans[i][2].equals(user_ans[i])) {

				System.out.println("\n Correct!");

				correct++;

			}

			else

			{

				System.out.println("\n Incorrect. The correct answer is " + Ques_Ans[i][2]);

				incorrect++;

			}

			System.out.print("\n");

			i++;

		} while (i < questions);

		System.out.println("\n Number of correct answers: " + correct);

		System.out.println("\n Number of incorrect answers: " + incorrect);

		System.exit(0);

	}

	private static void isValid(String[] user_ans) {
		// TODO Auto-generated method stub

	}

}
