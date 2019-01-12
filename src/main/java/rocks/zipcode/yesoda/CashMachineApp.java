package rocks.zipcode.yesoda;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.FlowPane;
import rocks.zipcode.yesoda.bank.Bank;

/**
 * @author ZipCodeWilmington
 */
public class CashMachineApp extends Application {

    private TextField fieldID = new TextField();
    private TextField fieldWithdraw = new TextField();
    private CashMachine cashMachine = new CashMachine(new Bank());
    Button btnDeposit = new Button();
    Button btnWithdraw = new Button();

    private Parent createContent() {
        VBox vbox = new VBox(10);
        Label mainLabel = new Label();
        mainLabel.setText("Welcome to ZIP CODE \n CASH MACHINE");
        vbox.setPrefSize(600, 600);
        String strTemp;

        TextArea areaInfo = new TextArea();

        Button btnSubmit = new Button("Enter Your AccountID");
        btnSubmit.setOnAction(e -> {

            int id = Integer.parseInt(fieldID.getText());
            cashMachine.login(id);

            if (cashMachine.toString().toString().equalsIgnoreCase("1")) {
                showAlertWithHeaderText("ERROR","" ,"Enter a valid Account number - 1000 or 2000 ");
            } else {
                areaInfo.setText(cashMachine.toString());
                onValidIDButtonIdClick(btnSubmit);
            }
        });

        Button btnParentExit = new Button("Exit");
        btnParentExit.setOnAction(e -> {
            cashMachine.exit();

            //areaInfo.setText(cashMachine.toString());
        });

        FlowPane flowpane = new FlowPane();

        mainLabel.setLayoutX(100);
        mainLabel.setLayoutY(100);
        vbox.setAlignment(Pos.TOP_CENTER);
        fieldID.setMaxSize( 200, 20);
        vbox.getChildren().addAll(mainLabel, btnSubmit, fieldID, flowpane, btnParentExit);
        return vbox;
    }


    public void onValidIDButtonIdClick(Button btnSubmit) {
        FlowPane flow = new FlowPane();
        Scene secondScene = new Scene(flow, 600, 600);
        Stage secondStage = new Stage();

        TextField fieldDeposit = new TextField();
        flow.setAlignment(Pos.TOP_LEFT);
        //fieldDeposit.setText("Enter the Amount to Deposit - ");
        flow.setAlignment(Pos.TOP_RIGHT);
        flow.getChildren().add(fieldDeposit);
        Button btnDeposit = new Button("Deposit");
        flow.getChildren().add(btnDeposit);
        TextField fieldWithDraw = new TextField();
        flow.getChildren().add(fieldWithDraw);
        Button btnWithdraw = new Button("WithDraw");
        flow.getChildren().add(btnWithdraw);
        TextArea areaInfo = new TextArea();
        Button btnExit = exitButtonDisplay(secondStage );
        flow.setPadding(new Insets(10, 10, 10, 10));
        flow.setStyle("-fx-background-color: DAE6F3;");
        flow.setHgap(10);
        flow.getChildren().add(btnExit );
        flow.getChildren().add (areaInfo);
        secondStage.setScene(secondScene); // set the scene
        secondStage.setTitle(" Please select the Bank Operation you want to perform ");
        secondStage.show();
        // close the first stage (Window)

        btnDeposit.setOnAction(e -> {
            int amount = Integer.parseInt(fieldDeposit.getText());
            cashMachine.deposit(amount);

            showAlertWithHeaderText("Deposit Success","" ,"Deposited Amount Successfully");
            areaInfo.setPrefRowCount(8);
            areaInfo.setText("Amount deposited now - " + amount + " \n ----Account Summary ----  \n" + cashMachine.toString());
            areaInfo.setText(cashMachine.toString());
            //cashMachine.toString();
        }) ;

        btnWithdraw.setOnAction(e -> {
            int amount = Integer.parseInt(fieldWithDraw .getText());
            cashMachine.withdraw(amount);
            showAlertWithHeaderText("Withdraw","" ,"Withdraw Amount Successfully");
            areaInfo.setText(cashMachine.toString());
        });




    }

   /* public void onDepositButtonClick()
    {
        Button btnDeposit = new Button("Deposit");
        btnDeposit.setOnAction(e -> {
            int amount = Integer.parseInt(fieldID.getText());
            cashMachine.deposit(amount);

           // areaInfo.setText(cashMachine.toString());
        });
    }*/

    public Button exitButtonDisplay(Stage stage) {

        Button btnExit = new Button("Exit");
        btnExit.setOnAction(e ->

        {
            cashMachine.exit();
            stage.close();

            //areaInfo.setText(cashMachine.toString());
        });
        return btnExit;


    }

        private void showAlertWithHeaderText(String title,String headertxt,String content ) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(headertxt);
            alert.setContentText(content);

            alert.showAndWait();
        }


    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(createContent()));
        stage.show();
        //stage.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
