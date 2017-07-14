package objects.add;

import java.awt.datatransfer.*;
import java.awt.Toolkit;

import objects.Post;
import objects.ticket.Ticket;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;

public class Add {

    public Add(Ticket ticket, Post post) {
        String testStr = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            String param = "from=" + ticket.getFrom().value +
                    "&to=" + ticket.getTill().value +
                    "&train=" + URLEncoder.encode(ticket.train_nbr, "UTF-8") +
                    "&date=" + format.format(ticket.depDate) +
                    "&round_trip=0" +
                    "&places[0][ord]=0" +
                    "&places[0][charline]=" +
                    "&places[0][wagon_num]=" + ticket.coach_num +
                    "&places[0][wagon_class]=" + URLEncoder.encode(ticket.coach_class, "UTF-8") +
                    "&places[0][wagon_type]=" + URLEncoder.encode(ticket.coach_type, "UTF-8") +
                    "&places[0][firstname]=" + URLEncoder.encode(ticket.firstName, "UTF-8") +
                    "&places[0][lastname]=" + URLEncoder.encode(ticket.lastName, "UTF-8") +
                    "&places[0][bedding]=0" +
                    "&places[0][child]=" +
                    "&places[0][stud]=" +
                    "&places[0][transportation]=0" +
                    "&places[0][reserve]=0" +
                    "&places[0][place_num]=" + ticket.place_nbr;
            System.out.println(param);
            testStr = (String) post.sendPost("http://booking.uz.gov.ua/cart/add/", param, null, ticket);
            System.out.println(param);
            if (testStr.contains("\"error\":false,\"data\":null,\"captcha\":null")) { //перевірити чи не було помилки
                ticket.cookieStore.setVisible(true);
                StringSelection stringSelection = new StringSelection(ticket.cookieStore.getText());
                Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
                clpbrd.setContents(stringSelection, null);
                ticket.instruction1.setVisible(true);
                ticket.instruction2.setVisible(true);
            } else {
                ticket.instruction1.setText("Помилка");
                ticket.instruction2.setText(testStr);
            }
        } catch (Exception e) {
            System.out.println("Add send error");
            ticket.instruction1.setText("Помилка");
            if (testStr != null)
                ticket.instruction2.setText(testStr);
        }
    }
}
