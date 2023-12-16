package controllers;

import Mappers.DividendMapper;
import bot.MessageProviders.DividendCalendarMP;
import db.DbPortfolioApi;
import dto.Dividend;
import http.Client;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.ArrayList;
import java.util.List;

public class DividendController {

    public static String getDividendCalendar(User user, String chatId) {
        List<String> portfolio = DbPortfolioApi.getPortfolio(user, chatId);

        List<Dividend> dividends = new ArrayList<>();
        String divRaw = Client.getDividends();

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

        //Filter common div list to portfolio
        List<Dividend> divFiltered = new ArrayList<>();
        for (Dividend d : dividends) {
            if(portfolio.contains(d.getTicker()))
                divFiltered.add(d);
        }

        return DividendCalendarMP.getDividendCalendar(divFiltered);
    }


}
