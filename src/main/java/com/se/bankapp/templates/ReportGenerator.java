package com.se.bankapp.templates;

import java.util.List;

public abstract class ReportGenerator {
    public String generateReport() {
        List<String> data = fetchData();
        String formatted = formatData(data);
        return export(formatted);
    }

    protected abstract List<String> fetchData();
    protected abstract String formatData(List<String> data);
    protected abstract String export(String report);
}