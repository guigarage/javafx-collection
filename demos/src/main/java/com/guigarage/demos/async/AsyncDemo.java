package com.guigarage.demos.async;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import io.datafx.core.concurrent.ProcessChain;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;


public class AsyncDemo extends Application {

    private ListView<String> listView;

    private Button actionButton;

    public static void sleep() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String... args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        listView = new ListView<>();
        actionButton = new Button("Action!");
        actionButton.setOnAction(e -> createChain1().run());

        ProgressBar progressBar = new ProgressBar();

        VBox box = new VBox(progressBar, listView, actionButton);
        box.setSpacing(12);
        box.setPadding(new Insets(12));
        Scene scene = new Scene(box);
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(e -> System.exit(0));
        primaryStage.show();
    }

    private ProcessChain createUglySolution() {
        return ProcessChain.create().
                addRunnableInPlatformThread(() -> {
                    actionButton.setDisable(true);
                    listView.getItems().clear();
                    List<String> list = new ArrayList<>();
                    for (int i = 0; i < 10; i++) {
                        list.add("Value " + i);
                        sleep();
                    }
                    listView.getItems().addAll(list);
                    actionButton.setDisable(false);
                });
    }

    private ProcessChain createChain1() {
        return ProcessChain.create().
                addRunnableInPlatformThread(() -> actionButton.setDisable(true)).
                addRunnableInPlatformThread(() -> listView.getItems().clear()).
                addSupplierInExecutor(() -> {
                    List<String> list = new ArrayList<>();
                    for (int i = 0; i < 10; i++) {
                        list.add("Value " + i);
                        sleep();
                    }
                    return list;
                }).
                addConsumerInPlatformThread((l) -> listView.getItems().addAll(l)).
                addRunnableInPlatformThread(() -> actionButton.setDisable(false));
    }

    private ProcessChain createChain2() {
        return ProcessChain.create().
                addRunnableInPlatformThread(() -> actionButton.setDisable(true)).
                addRunnableInPlatformThread(() -> listView.getItems().clear()).
                addSupplierInExecutor((Supplier<List<String>>) () -> {
                    sleep();
                    sleep();
                    sleep();
                    sleep();
                    throw new RuntimeException("ARGH!");
                }).
                addConsumerInPlatformThread((l) -> listView.getItems().addAll(l)).
                onException(e -> actionButton.setText("Error!")).
                withFinal(() -> actionButton.setDisable(false));
    }

    private ProcessChain createChain3() {
        return ProcessChain.create().
                addRunnableInPlatformThread(() -> actionButton.setDisable(true)).
                addRunnableInPlatformThread(() -> listView.getItems().clear()).
                addPublishingTask(() -> listView.getItems(), p -> {
                    for (int i = 0; i < 10; i++) {
                        p.publish("Value " + i);
                        sleep();
                    }
                }).
                onException(e -> actionButton.setText("Error!")).
                withFinal(() -> actionButton.setDisable(false));
    }

    private ProcessChain createWithHystrix() {
        return ProcessChain.create().
                addRunnableInPlatformThread(() -> actionButton.setDisable(true)).
                addRunnableInPlatformThread(() -> listView.getItems().clear()).
                addSupplierInExecutor((Supplier<List<String>>) () -> new CommandGetData().execute()).
                addConsumerInPlatformThread((l) -> listView.getItems().addAll(l)).
                onException(e -> actionButton.setText("Error!")).
                withFinal(() -> actionButton.setDisable(false));
    }
}

class CommandGetData extends HystrixCommand<List<String>> {

    public CommandGetData() {

        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("GetDataClient"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionIsolationThreadTimeoutInMilliseconds(6000)
                        .withExecutionIsolationThreadInterruptOnTimeout(true)
                        .withFallbackEnabled(false)));
    }

    @Override
    protected List<String> run() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            System.out.println("Adding: " + i);
            list.add("Value " + i);
            AsyncDemo.sleep();
        }
        return list;
    }

}