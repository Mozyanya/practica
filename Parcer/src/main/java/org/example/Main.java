package org.example;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static List<String> add = new ArrayList<>();
    static List<String> add2 = new ArrayList<>();
    static List<String> add1 = new ArrayList<>();

    public static void main(String[] argc) throws InterruptedException, SQLException {
        System.setProperty("webdriver.edge.driver", "D:\\WebDriver\\msedgedriver.exe");
        EdgeOptions edgeDriver1 = new EdgeOptions();
        WebDriver edgeDriver = new EdgeDriver(edgeDriver1);
        try {
            String url = "https://xn----btbhgbpv1d7d.xn--80aswg/kupit-zhd-bilety/#/vologda/moskva";
            edgeDriver.get(url);
            WebDriverWait webDriverWait = new WebDriverWait(edgeDriver, Duration.ofSeconds(20));
            webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.className("wg-train-container")));
            String pageSource = edgeDriver.getPageSource();
            Document aVoid = Jsoup.parse(pageSource);
            Elements5(aVoid);
            secondMetod();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            edgeDriver.quit();
        }
    }

    static void Elements5(Document document) {
        org.jsoup.select.Elements Elements1 = document.select("span.wg-train-info__number-link");
        org.jsoup.select.Elements Elements2 = document.select("span.wg-track-info__date");
        org.jsoup.select.Elements Element3 = document.select("span.wg-track-info__duration-time");
        for (Element element : Elements1) {
            String trainNumber = element.text();
            if (trainNumber.length() <= 7) {
                add.add(trainNumber);
            }
        }
        for (Element element : Elements2) {
            add1.add(element.text());
        }
        for (Element element : Element3) {
            add2.add(element.text());
        }
    }

    private static void secondMetod() throws SQLException, IOException {
        int j = 0;
        for (int i = 0; i < add.size(); i++) {
            Elements elements = new Elements();
            elements.setTrainId(add.get(i));
            elements.setTrainDuration(add2.get(i));
            elements.setTrainStart(add1.get(j));
            elements.setTrainEnd(add1.get(j + 1));
            System.out.println("Номер поезда: " + elements.getTrainId());
            System.out.println(elements.getTrainStart() + "------" + elements.getTrainDuration() + "------" + elements.getTrainEnd());
            System.out.println();
            j += 2;
        }
    }

    public static void ternmetod(Elements train) throws SQLException {
        String sql = "INSERT INTO public.megatrain(" +
                " trainname, traingo, trainduration, trainarrival)" +
                "\tVALUES (?, ?, ?, ?);";
        try (Connection connect = DB.connectDb(); PreparedStatement preparedStatement = connect.prepareStatement(sql)) {

            preparedStatement.setString(1, train.getTrainEnd());
            preparedStatement.setString(2, train.getTrainDuration());
            preparedStatement.setString(3, train.getTrainId());
            preparedStatement.setString(4, train.getTrainStart());

            preparedStatement.execute();
        }
    }
}

class Elements {
    private String trainId;
    private String trainDuration;
    private String trainStart;
    private String trainEnd;

    // геттеры и сеттеры
    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    public String getTrainDuration() {
        return trainDuration;
    }

    public void setTrainDuration(String trainDuration) {
        this.trainDuration = trainDuration;
    }

    public String getTrainStart() {
        return trainStart;
    }

    public void setTrainStart(String trainStart) {
        this.trainStart = trainStart;
    }

    public String getTrainEnd() {
        return trainEnd;
    }

    public void setTrainEnd(String trainEnd) {
        this.trainEnd = trainEnd;
    }
}
