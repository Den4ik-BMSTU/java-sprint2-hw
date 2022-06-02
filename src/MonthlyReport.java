import java.util.ArrayList;
import java.util.HashMap;

public class MonthlyReport {
    HashMap<Integer, ArrayList<MonthlyReportRecord>> monthlyReportRecords;
    String[] monthName = {"январь", "февраль", "март", "апрель", "май", "июнь", "июль", "август", "сентябрь", "октябрь", "ноябрь", "декабрь"};
    FileReader fileReader = new FileReader();

    MonthlyReport() {
        monthlyReportRecords = new HashMap<>();
    }

    void readMonthlyRecords() {
        for (int month = 1; month <= 3; month++) {
            ArrayList<MonthlyReportRecord> recordMonth = new ArrayList<>();
            String monthRecord = fileReader.readFileContentsOrNull("resources/m.20210" + month + ".csv");
            String[] lines = monthRecord.split("\n");
            for (int i = 1; i < lines.length; i++) {
                String line = lines[i];
                String[] parts = line.split(",");
                String itemName = parts[0];
                boolean isExpense = Boolean.parseBoolean(parts[1]);
                int quantity = Integer.parseInt(parts[2]);
                int sumOfOne = Integer.parseInt(parts[3]);
                MonthlyReportRecord monthlyRecord = new MonthlyReportRecord(itemName, isExpense, quantity, sumOfOne);
                recordMonth.add(monthlyRecord);
                monthlyReportRecords.put(month, recordMonth);
            }
        }
    }

    int getSumIncomeOrExpenseOfTheMonth(int monthKey, boolean isExpense) {
        int sum = 0;
        for (MonthlyReportRecord month : monthlyReportRecords.get(monthKey)) {
            if (month.isExpense == isExpense) {
                sum += month.quantity * month.sumOfOne;
            }
        }
        return sum;
    }

    String getMaxIncome(int monthKey) {
        int maxIncome = 0;
        String item;
        String maxItem = "";
        int sum;
        for (MonthlyReportRecord monthInfo : monthlyReportRecords.get(monthKey)) {
            if (!monthInfo.isExpense) {
                sum = monthInfo.quantity * monthInfo.sumOfOne;
                item = monthInfo.itemName;
                if (sum > maxIncome) {
                    maxIncome = sum;
                    maxItem = item;
                }
            }
        }
        return "Самый прибыльный товар: " + maxItem + "\nСумма: " + maxIncome;
    }

    String getMaxExpense(int monthKey) {
        int maxExpense = 0;
        String item;
        String maxExpenseItem = "";
        int sum;
        for (MonthlyReportRecord monthInfo : monthlyReportRecords.get(monthKey)) {
            if (monthInfo.isExpense) {
                sum = monthInfo.quantity * monthInfo.sumOfOne;
                item = monthInfo.itemName;
                if (sum > maxExpense) {
                    maxExpense = sum;
                    maxExpenseItem = item;
                }
            }
        }
        return "Больше всего потрачено: " + maxExpenseItem + "\nСумма: " + maxExpense;
    }

    void getMonthlyRecords() {
        if (monthlyReportRecords.isEmpty()) {
            readMonthlyRecords();
            System.out.println("Месячные отчеты загружены...");
        } else {
            System.out.println("Данные уже загружены...");
        }
    }

    void printMonthReport() {
        if (!monthlyReportRecords.isEmpty()) {
            for (int month : monthlyReportRecords.keySet()) {
                System.out.println("Отчет за " + monthName[month - 1] + ":");
                System.out.println(getMaxIncome(month));
                System.out.println(getMaxExpense(month));
            }
        } else {
            System.out.println("Отчет еще не загружен...");
        }
    }
}
class MonthlyReportRecord {
    String itemName;
    boolean isExpense;
    int quantity;
    int sumOfOne;

    MonthlyReportRecord(String itemName, boolean isExpense, int quantity, int sumOfOne) {
        this.itemName = itemName;
        this.isExpense = isExpense;
        this.quantity = quantity;
        this.sumOfOne = sumOfOne;
    }
}