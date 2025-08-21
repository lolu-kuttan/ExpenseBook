 @FXML
    private void handleSearch() {
        String dateFilter = dateSearchField.getText().trim();
        String categoryFilter = categorySearchField.getText().trim();

        String sql = "SELECT * FROM expenses WHERE 1=1";
        if (!dateFilter.isEmpty()) {
            sql += " AND date LIKE ?";
        }
        if (!categoryFilter.isEmpty()) {
            sql += " AND category LIKE ?";
        }

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            int paramIndex = 1;
            if (!dateFilter.isEmpty()) {
                stmt.setString(paramIndex++, "%" + dateFilter + "%");
            }
            if (!categoryFilter.isEmpty()) {
                stmt.setString(paramIndex, "%" + categoryFilter + "%");
            }

            ResultSet rs = stmt.executeQuery();
            expenseList.clear();

            while (rs.next()) {
                expenseList.add(new Expense(
                        rs.getString("date"),
                        rs.getString("category"),
                        rs.getDouble("amount"),
                        rs.getString("description")
                ));
            }

            expenseTable.setItems(expenseList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        double total = 0.0;
        for (Expense expense : expenseList) {
            total += expense.getAmount();          //display the total expenses
        }
        totalLabel.setText(String.format("Total: ₹ %.2f", total));

    }

    @FXML
    private void handleClearSearch() {               // clears the data on the search bar
        dateSearchField.clear();
        categorySearchField.clear();
        loadExpenses(); // reload all data
        double total = 0.0;
        for (Expense expense : expenseList) {
            total += expense.getAmount();
        }
        totalLabel.setText(String.format("Total: ₹ %.2f", total));

    }
}
