import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;
//-------------------------------------------------------------------
/*
-Font för event = Ink Free

För att positionera med GridBagLayout tog jag hjälp av denna videon:
https://www.youtube.com/watch?v=ZipG38DJJK8&ab_channel=mybringback
 */
//--------------------------------------------------------------------

public class CalenderGUI {
    public static void calenderGUI() {
    //Skapar ett frame objekt med GridLayout som layout för att få 7 kolumner för vardera dag
        JFrame frame = new JFrame();
        //Frame inställningar
        frame.setSize(new Dimension(1000, 600));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(1, 7));
        frame.setTitle("Calender");

        //Skapar två LocalDate objekt, en för dagens datum och en för vilken dag veckan börjar på
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);

        //Skapar nytt Locale objekt med svenkskt språk och sätter svenska som standard språk
        Locale swedish = new Locale("sv");
        Locale.setDefault(swedish);

        //Skapar objektet "gbc" för att kunna positionera komponenterna och lägger till marginal med hjälp av "insets"
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        //Specifiierar att komponenterna ska placeras på toppen av panelen som standard
        gbc.anchor = GridBagConstraints.NORTH;

        //Skapar variabeln "currentDay" för att hålla koll på vilken dag det är
        int currentDay = today.getDayOfWeek().getValue();

        //-----------------------------------------------------------------
        //For-loop som itererar sju gånger för att lägga till alla veckans dagar som paneler och skapar komponenter för varje dag
        for (int i = 0; i < 7; i++ ) {
        //Skapar ny LocalDate som börjar på första dagen i veckan och plussar varje gång det görs en iteration
            LocalDate weekDays = startOfWeek.plusDays(i);

        //Skapar 7 paneler för varje dag
            JPanel dayPanel = new JPanel();
            //Sätter border runt alla paneler
            dayPanel.setBorder(BorderFactory.createLineBorder(new Color(0xA6C0C0C0, true)));
            //Sätter GridBagLayout som layout i panelerna
            dayPanel.setLayout(new GridBagLayout());
            dayPanel.setBackground(new Color(0xFF363636, true));
            frame.add(dayPanel);

        //Skapar label som representerar dagens dag
            JLabel dayLabel = new JLabel();
            //Sätter text i label från weekDays med dagens dag som plussar till nästa dag varje gång det itererar
            dayLabel.setText(weekDays.getDayOfWeek().getDisplayName(TextStyle.FULL, swedish).toUpperCase());
            //Font settings för dayLabel
            dayLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
            dayLabel.setForeground(Color.white);

        //Skapar en label som representerar datum
            JLabel dateLabel = new JLabel();
            //Sätter text i label från weekdays som plussar till nästa dag varje gång det itererar.
            dateLabel.setText(weekDays.toString());
            //Font settings för dateLabel
            dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            dateLabel.setForeground(Color.white);

        //Skapar ett textfield där användaren kan skriva in sin händelse
            JTextField eventField = new JTextField();
            //Settings för eventField
            eventField.setMinimumSize(new Dimension(120, 20));
            eventField.setPreferredSize(new Dimension(120, 20));

        //Skapar en knapp för att kunna lägga in händelser
            JButton eventButton = new JButton("Lägg till händelse");
            eventButton.setBackground(new Color(127, 189, 253, 255));
            eventButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        //Om current day är samma som iterationen + 1, markera dayLabel och dateLabel med ny färg
            if (currentDay == i + 1) {
                //Skapar en ny label som representerar en live klocka på den markerade panelen
                JLabel clockLabel = new JLabel();
                clockLabel.setForeground(Color.white);
                gbc.gridy = 3;
                dayPanel.add(clockLabel, gbc);

                //Ändrar färg på dateLabel och dayLabel
                dayLabel.setForeground(new Color(127, 189, 253, 255));
                dateLabel.setForeground(new Color(127, 189, 253, 255));
                /*
                -------------------------------------------------------------------------
                EXTRAFUNKTION FÖR VG
                Kodblock taget från:
                https://www.youtube.com/watch?v=HJzxS7eXtD0&ab_channel=StudyViral
                (Kolla 8:02 minuter in för att se exakt kodblock jag tagit)
                 */
                Timer timer = new Timer(1000, new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Date date = new Date();
                        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", swedish);
                        String time1 = timeFormat.format(date);
                        clockLabel.setText(time1);
                        clockLabel.setFont(new Font("Ink Free", Font.BOLD, 16));
                    }
                });
                timer.start();
                //------------------------------------------------------------------------
            }
            //Actionlistener för eventButton
            eventButton.addActionListener(e -> {
                //Skapa nytt eventLabel och sätt texten från eventField in i eventLabel
                JLabel eventLabel = new JLabel();
                eventLabel.setText(eventField.getText());
                eventLabel.setForeground(Color.white);
                eventLabel.setFont(new Font("Ink Free", Font.BOLD, 16));

                //GridBagLayout inställningar för att positionera eventLabel
                gbc.gridx = 0;
                gbc.gridy = 2;
                gbc.weighty = 6;
                dayPanel.add(eventLabel, gbc);

                //Rensa textrutan efter tryck
                eventField.setText("");

                //Skapar en rensa knapp för att kunna rensa händelsen man lägger in
                JButton clearText = new JButton("Rensa");
                clearText.setBackground(new Color(253, 127, 127, 255));
                gbc.gridx = 0;
                gbc.gridy = 6;
                gbc.weighty = 0;
                dayPanel.add(clearText, gbc);

                //Actionlistener för rensaknappen som rensar händelsefältet och gömmer knappen igen
                clearText.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        eventLabel.setText("");
                        clearText.setVisible(false);
                    }
                });
                frame.repaint();
                frame.revalidate();
            });

    //--------------------------------------------------------------------------
            //Lägger in komponenterna i panelerna
            //Positionerar med hjälp av "gridY" då jag radar upp komponenterna vertikalt (Y) och värdet sätter rätt ordning på komponenterna
            //weighty skapar mellanrum mellan datelabel och eventfield

            //Lägger in dayLabel komponent i panelerna med GridBagLayout inställningar
            gbc.gridy = 0;
            gbc.weighty = 0;
            dayPanel.add(dayLabel, gbc);

            //Lägger in dateLabel komponent i panelerna med GridBagLayout inställningar
            gbc.gridy = 1;
            gbc.weighty = 8;
            dayPanel.add(dateLabel, gbc);

            //Lägger in eventField komponent i panelerna med GridBagLayout inställningar
            gbc.gridy = 4;
            gbc.weighty = 0;
            dayPanel.add(eventField, gbc);

            //Lägger in eventButton komponent i panelerna med GridBagLayout inställningar
            gbc.gridy = 5;
            gbc.weighty = 0;
            dayPanel.add(eventButton, gbc);
        }
    //-------------------------------------------------------------------------
        frame.pack();
        //Sätter frame size igen då frame pack gjorde fönstret litet
        frame.setSize(new Dimension(1000, 600));
        frame.setVisible(true);

    }
}
