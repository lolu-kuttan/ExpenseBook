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

