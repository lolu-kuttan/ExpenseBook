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

    private Callback<TableColumn<Expense, Void>, TableCell<Expense, Void>> getActionCellFactory() {
        return new Callback<>() {
            @Override
            public TableCell<Expense, Void> call(final TableColumn<Expense, Void> param) {
                return new TableCell<>() {
                    private final Button editButton = new Button("Edit");
                    private final Button deleteButton = new Button("Delete");
                    private final HBox pane = new HBox(5, editButton, deleteButton);

                    {
                        editButton.setOnAction(e -> {
                            Expense expense = getTableView().getItems().get(getIndex());
                            showEditDialog(expense);
                        });

                        deleteButton.setOnAction(e -> {
                            Expense expense = getTableView().getItems().get(getIndex());
                            deleteExpense(expense);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(pane);
                        }
                    }
                };
            }
        };
    }
