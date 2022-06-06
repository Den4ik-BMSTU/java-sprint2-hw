class AccountingAutomation {
    MonthlyReport monthlyReport;
    YearlyReport yearlyReport;

    AccountingAutomation() {
        monthlyReport = new MonthlyReport();
        yearlyReport = new YearlyReport();
    }

    void reconciliationOfReports() {
        if (yearlyReport.isYearlyReportsExists() && monthlyReport.monthlyReportRecords.isEmpty()) {
            System.out.println("Годовой и месячный отчет еще не загружены...");
        } else if (yearlyReport.isYearlyReportsExists()) {
            System.out.println("Годовой отчет еще не загружен...");
        } else if (monthlyReport.monthlyReportRecords.isEmpty()) {
            System.out.println("Месячный отчет еще не загружен...");
        } else {
            report();
        }
    }
    void report() {
       boolean isValid = true;
        for (YearlyReportRecord yearlyReportRecord : yearlyReport.yearlyReportRecords) {
            if (yearlyReportRecord.isExpense) {
                if (monthlyReport.getSumIncomeOrExpenseOfTheMonth(yearlyReportRecord.month, true) != yearlyReportRecord.amount) {
                    System.out.println("Убыток за " + monthlyReport.monthName[yearlyReportRecord.month - 1] + " несоответствует годовому отчету");
                    isValid = false;}
                } else {
                    if (monthlyReport.getSumIncomeOrExpenseOfTheMonth(yearlyReportRecord.month, false) != yearlyReportRecord.amount) {
                        System.out.println("Доход за " + monthlyReport.monthName[yearlyReportRecord.month - 1] + " несоответствует годовому отчету");
                        isValid = false;
                    }
                }

            }
            if (isValid) {
                System.out.println("Сверка отчетов успешно завершена...");
            }
        }

    void printMenu() {
        System.out.println("1 -- Считать все месячные отчёты");
        System.out.println("2 -- Считать годовой отчёт");
        System.out.println("3 -- Сверить отчёты");
        System.out.println("4 -- Вывести информацию о всех месячных отчётах");
        System.out.println("5 -- Вывести информацию о годовом отчёте");
        System.out.println("0 -- Выход");
    }
}