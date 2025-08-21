  private void loadExpenses() {
        String sql = "SELECT * FROM expenses";

        try (Connection conn = DatabaseHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            expenseList.clear();

            while (rs.next()) {
                String date = rs.getString("date");
                String category = rs.getString("category");
                double amount = rs.getDouble("amount");
                String description = rs.getString("description");

                expenseList.add(new Expense(date, category, amount, description));
            }

            expenseTable.setItems(expenseList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
