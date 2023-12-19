package controllers;

import Mappers.DividendMapper;
import bot.MessageProviders.DividendCalendarMP;
import db.DbPortfolioApi;
import dto.Dividend;
import dto.Ticker;
import http.DividendApi;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.telegram.telegrambots.meta.api.objects.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DividendController {

    public static String getDividendCalendar(User user, String chatId) {
        List<Ticker> portfolio = DbPortfolioApi.getPortfolio(user, chatId);

        String divPreviousRaw = DividendApi.getDividends(LocalDate.now().minusMonths(3).toString(), LocalDate.now().toString());
        List<Dividend> divAllPrevious = parseDividend(divPreviousRaw);
        List<Dividend> divPrevious = filterPortfolioDivs(divAllPrevious, portfolio);

        String divNextRaw = DividendApi.getDividends(LocalDate.now().toString(), LocalDate.now().plusMonths(3).toString());
        List<Dividend> divAllNext = parseDividend(divNextRaw);
        List<Dividend> divNext = filterPortfolioDivs(divAllNext, portfolio);

        List<Dividend> dividends = new ArrayList<>();
        dividends.addAll(divPrevious);
        dividends.addAll(divNext);

        return DividendCalendarMP.getDividendCalendar(dividends);
    }

    private static List<Dividend> parseDividend(String divRaw) {
        List<Dividend> dividends = new ArrayList<>();

        if (divRaw != null) {
            JSONParser parser = new JSONParser();
            JSONArray json;

            try {
                json = (JSONArray) parser.parse(divRaw);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            for (int i = 0; i < json.size(); i++) {
                JSONObject jsonO = (JSONObject) json.get(i);
                dividends.add(DividendMapper.mapDividend(jsonO.get("symbol"), jsonO.get("dividend"), jsonO.get("recordDate"), jsonO.get("paymentDate")));
            }
        }
        return dividends;
    }

    private static List<Dividend> filterPortfolioDivs(List<Dividend> dividends, List<Ticker> portfolio) {
        List<Dividend> divFiltered = new ArrayList<>();
        List<String> tickersInPortfolio = new ArrayList<>();
        for (Ticker t : portfolio) {
            tickersInPortfolio.add(t.getTicker());
        }

        for (Dividend d : dividends) {
            if(tickersInPortfolio.contains(d.getTicker()))
                divFiltered.add(d);
        }
        return divFiltered;
    }

    private static String getFrom() {
        return LocalDate.now().minusMonths(3).toString();
    }

    private static String getTo() {
        return LocalDate.now().plusMonths(3).toString();
    }
}
