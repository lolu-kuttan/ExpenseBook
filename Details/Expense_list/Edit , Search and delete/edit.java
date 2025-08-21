// Edit_log

 private void showEditDialog(Expense expense) {
        TextInputDialog dialog = new TextInputDialog(expense.getAmount() + "");
        dialog.setTitle("Edit Expense");
        dialog.setHeaderText("Edit amount for: " + expense.getCategory());
        dialog.setContentText("New amount:");

        dialog.showAndWait().ifPresent(input -> {
            try {
                double newAmount = Double.parseDouble(input);
                updateExpenseAmount(expense, newAmount);
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount entered.");
            }
        });
    }
 private void updateExpenseAmount(Expense expense, double newAmount) {
        String sql = "UPDATE expenses SET amount = ? WHERE date = ? AND category = ? AND description = ?";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, newAmount);
            stmt.setString(2, expense.getDate());
            stmt.setString(3, expense.getCategory());
            stmt.setString(4, expense.getDescription());

            stmt.executeUpdate();

            expense.setAmount(newAmount);
            expenseTable.refresh();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

