import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProgramMenu {
    private JPanel functionPanel;
    private JButton derivativeButton, addTermButton, removeTermButton;
    private JTextField [] terms;
    private int termCount;
    private JLabel rules, plus;
    private JLabel [] plusses;

    public ProgramMenu() {
        ButtonListener b = new ButtonListener();
        JFrame window = new JFrame("Derivative Calculator");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        window.setLayout(new GridLayout(4, 1));
        rules = new JLabel("Type in a function (ex: 2x^2, (4x^3+3)^8, 6x(x+1)^2/(5x^2+3)^4)." +
                " One set of parentheses per numerator/denominator. One fully simplified term per box.");
        rules.setHorizontalAlignment(JLabel.CENTER);
        rules.setOpaque(true);
        window.add(rules);

        functionPanel = new JPanel();
        functionPanel.setVisible(true);
        functionPanel.setLayout(new GridLayout(1, 15));
        terms = new JTextField [5];
        terms[0] = new JTextField("0");
        terms[0].setHorizontalAlignment(JLabel.CENTER);
        terms[0].setPreferredSize(new Dimension(100, 50));
        functionPanel.add(terms[0]);
        window.add(functionPanel);

        addTermButton = new JButton("Add Term");
        removeTermButton = new JButton("Remove Term");
        derivativeButton = new JButton("Derive");

        addTermButton.addActionListener(b);
        derivativeButton.addActionListener(b);
        removeTermButton.addActionListener(b);

        termCount = 1;
        removeTermButton.setEnabled(false);
        JPanel addRemovePanel = new JPanel();
        addRemovePanel.setLayout(new GridLayout(1, 1));
        addRemovePanel.add(addTermButton);
        addRemovePanel.add(removeTermButton);

        window.add(addRemovePanel);
        window.add(derivativeButton);
        plusses = new JLabel [4];
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private class ButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == addTermButton) {
                if (termCount == 1) {
                    removeTermButton.setEnabled(true);
                } else if (termCount == 4) {
                    addTermButton.setEnabled(false);
                }

                terms[termCount] = new JTextField("0");
                terms[termCount].setHorizontalAlignment(JLabel.CENTER);
                terms[termCount].setPreferredSize(new Dimension(100, 50));
                plus = new JLabel("+");
                plus.setOpaque(true);
                plusses[termCount - 1] = plus;

                if (termCount == 1) {
                    for (JLabel plus : plusses) {
                        if (plus == null)
                            break;
                        plus.setBackground(new Color(204, 204, 255));
                    } rules.setBackground(new Color(204, 204, 255));
                }

                else if (termCount == 2) {
                    for (JLabel plus : plusses) {
                        if (plus == null)
                            break;
                        plus.setBackground(new Color(204, 217, 255));
                    } rules.setBackground(new Color(204, 217, 255));
                }

                else if (termCount == 3) {
                    for (JLabel plus : plusses) {
                        if (plus == null)
                            break;
                        plus.setBackground(new Color(204, 230, 255));
                    } rules.setBackground(new Color(204, 230, 255));
                }

                else if (termCount == 4) {
                    for (JLabel plus : plusses) {
                        if (plus == null)
                            break;
                        plus.setBackground(new Color(204, 242, 255));
                    } rules.setBackground(new Color(204, 242, 255));
                }

                plus.setHorizontalAlignment(JLabel.CENTER);
                functionPanel.add(plus);
                functionPanel.add(terms[termCount]);
                rules.revalidate();
                functionPanel.revalidate();
                ++termCount;
            }

            else if (e.getSource() == removeTermButton) {
                if (termCount == 2) {
                    removeTermButton.setEnabled(false);
                }

                else if (termCount == 3) {
                    for (JLabel plus : plusses) {
                        if (plus == null)
                            break;
                        plus.setBackground(new Color(204, 204, 255));
                    } rules.setBackground(new Color(204, 204, 255));
                }

                else if (termCount == 4) {
                    for (JLabel plus : plusses) {
                        if (plus == null)
                            break;
                        plus.setBackground(new Color(204, 217, 255));
                    } rules.setBackground(new Color(204, 217, 255));
                }

                else if (termCount == 5) {
                    for (JLabel plus : plusses) {
                        if (plus == null)
                            break;
                        plus.setBackground(new Color(204, 230, 255));
                    } rules.setBackground(new Color(204, 230, 255));
                    addTermButton.setEnabled(true);
                }

                functionPanel.remove(terms[termCount - 1]);
                functionPanel.remove(plusses[termCount - 2]);
                rules.revalidate();
                functionPanel.revalidate();
                --termCount;
            }

            else if (e.getSource() == derivativeButton) {
                StringBuilder derivatives = new StringBuilder();
                boolean allValid = true;
                for (int i = 0; i < termCount; ++i) {
                    Derivative derivative = new Derivative(terms[i].getText());
                    if (derivative.isValid()) {
                        derivatives.append(derivative.toString());
                        if (i != termCount - 1) {
                            derivatives.append('+');
                        }
                    } else {
                        allValid = false;
                        break;
                    }
                } if (allValid) {
                    JOptionPane.showMessageDialog(null, derivatives);
                }
            }
        }
    }
}
