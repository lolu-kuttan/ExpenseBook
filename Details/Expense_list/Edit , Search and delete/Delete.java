private void deleteExpense(Expense expense) {
        String sql = "DELETE FROM expenses WHERE date = ? AND category = ? AND amount = ? AND description = ?";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, expense.getDate());
            stmt.setString(2, expense.getCategory());
            stmt.setDouble(3, expense.getAmount());
            stmt.setString(4, expense.getDescription());

            stmt.executeUpdate();

            expenseList.remove(expense);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
