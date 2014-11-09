package ru.csc.ir.metrics;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Metrics {
    public static void main(String[] args) {
        List<Integer> grades = readData();

        System.out.println("DCG: " + dcg(grades));
        System.out.println("NDCG: " + ndcg(grades));
        System.out.println("PFound: " + pFound(grades));
    }

    private static List<Integer> readData() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        ArrayList<Integer> grades = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            grades.add(scanner.nextInt());
        }

        return grades;
    }

    private static double dcg(@NotNull List<Integer> grades) {
        double res = 0.0;
        int i = 1;
        for (Integer grade : grades) {
            res += (Math.pow(2.0, grade) - 1) / log2(i + 1);
            i++;
        }

        return res;
    }

    private static double ndcg(@NotNull List<Integer> grades) {
        return dcg(grades) / idcg(grades);
    }

    private static double pFound(@NotNull List<Integer> grades) {
        double pLook = 1.0;
        double pFound = 0.0;
        double pBreak = 0.15;
        double maxGrade = Collections.max(grades);

        for (Integer grade : grades) {
            double rel = pRel(grade, maxGrade);
            pFound += pLook * rel;
            pLook *= (1.0 - rel) * (1 - pBreak);
        }

        return pFound;
    }

    private static double pRel(double grade, double maxGrade) {
        return (Math.pow(2.0, grade) - 1) / Math.pow(2.0, maxGrade);
    }

    private static double idcg(@NotNull List<Integer> grades) {
        List<Integer> sortedGrades = new ArrayList<>(grades);
        sortedGrades.sort(Collections.reverseOrder());
        return dcg(sortedGrades);
    }

    private static double log2(double n) {
        return Math.log(n) / Math.log(2.0);
    }
}
