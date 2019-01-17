import javax.swing.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Derivative extends Function {
    private StringBuilder results;
    private DecimalFormat df;
    private double bottomCoefficient, bottomExponent, topLeftOutsideCoefficient, topLeftOutsideExponent,
            topRightOutsideCoefficient, topRightOutsideExponent, topLeftOutsideCoefficient2, topLeftOutsideExponent2,
            topRightOutsideCoefficient2, topRightOutsideExponent2, bottomOutsideCoefficient, bottomOutsideExponent,
            oldNumOutsideCoefficient, oldNumOutsideExponent, oldDenomOutsideCoefficient, oldDenomOutsideExponent,
            numOutsideCoefficient2, denomOutsideCoefficient2, oldNumExponent, oldDenomExponent;
    private ArrayList<Double> oldNumParensCoefficients, oldNumParensExponents, oldDenomParensCoefficients,
            oldDenomParensExponents, numParensExponents2, denomParensExponents2, topLeftParensCoefficients, topLeftParensExponents,
            topLeftParensCoefficients2, topLeftParensExponents2, topLeftParensCoefficients3, topLeftParensExponents3,
            topRightParensCoefficients, topRightParensExponents, topRightParensCoefficients2, topRightParensExponents2,
            topRightParensCoefficients3, topRightParensExponents3, topRightParensCoefficients4, topRightParensExponents4,
            topRightParensCoefficients5, topRightParensExponents5, bottomParensCoefficients, bottomParensExponents,
            topLeftParensCoefficients4, topLeftParensExponents4, topLeftParensCoefficients5, topLeftParensExponents5;

    public Derivative(String function) {
        super(function);
        if (isValid()) {
            try {
                derive();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Invalid input.");
            }
        }
    }

    // derive() initiates the process of finding the derivative of the function
    private void derive() {
        if (numExponent == 0 && numParensExponents.size() == 0) {
            if (denomExponent == 0 && denomParensCoefficients.size() == 0) {
                numCoefficient = 0;
            }

            else if (denomExponent != 0) {
                denomCoefficient /= -denomExponent;
                ++denomExponent;
            }

            else {
                applyQuotientRule();
            }
        }

        else if (numExponent != 0 && numParensExponents.size() == 0) {
            oldNumExponent = numExponent;
            if (denomExponent == 0 && denomParensCoefficients.size() == 0) {
                numCoefficient *= numExponent;
                --numExponent;
            } else {
                applyQuotientRule();
            }
        }

        else if (numExponent == 0 && numParensExponents.size() != 0 && numOutsideExponent == 0) {
            if (denomExponent == 0 && denomParensCoefficients.size() == 0) {
                oldNumParensCoefficients = new ArrayList<>(numParensCoefficients);
                oldNumParensExponents = new ArrayList<>(numParensExponents);
                numParensExponents2 = new ArrayList<>(numParensExponents);

                for (int i = 0; i < numParensCoefficients.size(); ++i) {
                    numParensCoefficients.set(i, numParensCoefficients.get(i) * numParensExponents.get(i));
                    numParensExponents.set(i, numParensExponents.get(i) - 1);
                }

                numOutsideCoefficient *= numParensExponents.get(numParensExponents.size() - 1);
                numParensExponents.set(numParensExponents.size() - 1, 1.0);
                numParensExponents2.set(numParensExponents2.size() - 1,
                        numParensExponents2.get(numParensExponents2.size() - 1) - 1);

                topLeftOutsideCoefficient = numOutsideCoefficient;
                topLeftParensCoefficients = numParensCoefficients;
                topLeftParensExponents = numParensExponents;
                topLeftParensCoefficients2 = oldNumParensCoefficients;
                topLeftParensExponents2 = numParensExponents2;
            } else {
                applyQuotientRule();
            }
        }

        else if (numExponent == 0 && numParensExponents.size() != 0 && numOutsideExponent != 0) {
            if (denomExponent == 0 & denomParensCoefficients.size() == 0) {
                applyProductRule();
                topLeftOutsideCoefficient = numOutsideCoefficient;
                topLeftOutsideExponent = numOutsideExponent;
                topLeftParensCoefficients = oldNumParensCoefficients;
                topLeftParensExponents = oldNumParensExponents;

                topRightOutsideCoefficient = numOutsideCoefficient2;
                topRightOutsideExponent = oldNumOutsideExponent;
                topRightParensCoefficients = numParensCoefficients;
                topRightParensExponents = numParensExponents;
                topRightParensCoefficients2 = oldNumParensCoefficients;
                topRightParensExponents2 = numParensExponents2;
            } else {
                applyQuotientRule();
            }
        }
    }

    // applyProductRule() applies the product rule to solve for the derivative
    private void applyProductRule() {
        if (denomExponent == 0 && denomParensCoefficients.size() != 0 && denomOutsideExponent != 0) {
            oldDenomParensCoefficients = new ArrayList<>(denomParensCoefficients);
            oldDenomParensExponents = new ArrayList<>(denomParensExponents);
            denomParensExponents2 = new ArrayList<>(denomParensExponents);

            oldDenomOutsideCoefficient = denomOutsideCoefficient;
            oldDenomOutsideExponent = denomOutsideExponent;
            denomOutsideCoefficient2 = denomOutsideCoefficient;
            denomOutsideCoefficient *= denomOutsideExponent;
            --denomOutsideExponent;

            for (int i = 0; i < denomParensCoefficients.size(); ++i) {
                denomParensCoefficients.set(i, denomParensCoefficients.get(i) * denomParensExponents.get(i));
                denomParensExponents.set(i, denomParensExponents.get(i) - 1);
            }

            denomOutsideCoefficient2 *= denomParensExponents.get(denomParensExponents.size() - 1);
            denomParensExponents.set(denomParensExponents.size() - 1, 1.0);
            denomParensExponents2.set(denomParensExponents2.size() - 1,
                    denomParensExponents2.get(denomParensExponents2.size() - 1) - 1);
        }

        if (numExponent == 0 && numParensCoefficients.size() != 0 && numOutsideExponent != 0) {
            oldNumParensCoefficients = new ArrayList<>(numParensCoefficients);
            oldNumParensExponents = new ArrayList<>(numParensExponents);
            numParensExponents2 = new ArrayList<>(numParensExponents);

            oldNumOutsideCoefficient = numOutsideCoefficient;
            oldNumOutsideExponent  = numOutsideExponent;
            numOutsideCoefficient2 = numOutsideCoefficient;
            numOutsideCoefficient *= numOutsideExponent;
            --numOutsideExponent;

            for (int i = 0; i < numParensCoefficients.size(); ++i) {
                numParensCoefficients.set(i, numParensCoefficients.get(i) * numParensExponents.get(i));
                numParensExponents.set(i, numParensExponents.get(i) - 1);
            }

            numOutsideCoefficient2 *= numParensExponents.get(numParensExponents.size() - 1);
            numParensExponents.set(numParensExponents.size() - 1, 1.0);
            numParensExponents2.set(numParensExponents2.size() - 1,
                    numParensExponents2.get(numParensExponents2.size() - 1) - 1);
        }
    }

    // applyQuotientRule() applies the quotient rule to solve for the derivative
    private void applyQuotientRule() {
        if (denomExponent == 0 && denomParensCoefficients.size() != 0 && denomOutsideExponent == 0) {
            oldDenomParensCoefficients = new ArrayList<>(denomParensCoefficients);
            oldDenomParensExponents = new ArrayList<>(denomParensExponents);
            denomParensExponents2 = new ArrayList<>(denomParensExponents);

            oldDenomOutsideCoefficient = denomOutsideCoefficient;
            denomParensExponents2.set(denomParensExponents2.size() - 1,
                    denomParensExponents2.get(denomParensExponents2.size() - 1) - 1);
            denomParensExponents.set(denomParensExponents.size() - 1, 1.0);

            if (numExponent == 0 && numParensExponents.size() == 0) {
                for (int i = 0; i < denomParensExponents.size() - 1; ++i) {
                    denomParensCoefficients.set(i, denomParensCoefficients.get(i) * denomParensExponents.get(i));
                    denomParensExponents.set(i, denomParensExponents.get(i) - 1);
                }

                topRightOutsideCoefficient = -numCoefficient * denomOutsideCoefficient *
                        oldDenomParensExponents.get(oldDenomParensExponents.size() - 1);
                topRightParensCoefficients = denomParensCoefficients;
                topRightParensExponents = denomParensExponents;
                topRightParensCoefficients2 = oldDenomParensCoefficients;
                topRightParensExponents2 = denomParensExponents2;
            }

            else if (numParensExponents.size() != 0 && numOutsideExponent == 0) {
                oldNumParensCoefficients = new ArrayList<>(numParensCoefficients);
                oldNumParensExponents = new ArrayList<>(numParensExponents);
                numParensExponents2 = new ArrayList<>(numParensExponents);
                oldNumOutsideCoefficient = numOutsideCoefficient;

                for (int i = 0; i < numParensCoefficients.size(); ++i) {
                    numParensCoefficients.set(i, numParensCoefficients.get(i) * numParensExponents.get(i));
                    numParensExponents.set(i, numParensExponents.get(i) - 1);
                }

                numParensExponents.set(numParensExponents.size() - 1, 1.0);
                numParensExponents2.set(numParensExponents2.size() - 1,
                        numParensExponents2.get(numParensExponents2.size() - 1) - 1);
                topLeftOutsideCoefficient = oldDenomOutsideCoefficient * numOutsideCoefficient *
                        oldNumParensExponents.get(oldNumParensExponents.size() - 1);

                topLeftParensCoefficients = numParensCoefficients;
                topLeftParensExponents = numParensExponents;
                topLeftParensCoefficients2 = oldNumParensCoefficients;
                topLeftParensExponents2 = numParensExponents2;
                topLeftParensCoefficients3 = oldDenomParensCoefficients;
                topLeftParensExponents3 = oldDenomParensExponents;

                for (int i = 0; i < denomParensCoefficients.size(); ++i) {
                    denomParensCoefficients.set(i, denomParensCoefficients.get(i) * denomParensExponents.get(i));
                    denomParensExponents.set(i, denomParensExponents.get(i) - 1);
                }

                topRightOutsideCoefficient = -denomOutsideCoefficient * oldNumOutsideCoefficient *
                        oldDenomParensExponents.get(oldDenomParensExponents.size() - 1);
                topRightParensCoefficients = denomParensCoefficients;
                topRightParensExponents = denomParensExponents;
                topRightParensCoefficients2 = oldNumParensCoefficients;
                topRightParensExponents2 = oldNumParensExponents;
                topRightParensCoefficients3 = oldDenomParensCoefficients;
                topRightParensExponents3 = denomParensExponents2;
            }

            else if (numParensExponents.size() != 0 && numOutsideExponent != 0) {
                applyProductRule();
                topLeftOutsideCoefficient = oldDenomOutsideCoefficient * numOutsideCoefficient;
                topLeftOutsideExponent = numOutsideExponent;
                topLeftParensCoefficients = oldNumParensCoefficients;
                topLeftParensExponents = oldNumParensExponents;
                topLeftParensCoefficients2 = oldDenomParensCoefficients;
                topLeftParensExponents2 = oldDenomParensExponents;


                topLeftOutsideCoefficient2 = oldDenomOutsideCoefficient * numOutsideCoefficient2;
                topLeftOutsideExponent2 = oldNumOutsideExponent;
                topLeftParensCoefficients3 = numParensCoefficients;
                topLeftParensExponents3 = numParensExponents;
                topLeftParensCoefficients4 = oldDenomParensCoefficients;
                topLeftParensExponents4 = oldDenomParensExponents;
                topLeftParensCoefficients5 = oldNumParensCoefficients;
                topLeftParensExponents5 = numParensExponents2;


                for (int i = 0; i < denomParensCoefficients.size(); ++i) {
                    denomParensCoefficients.set(i, denomParensCoefficients.get(i) * denomParensExponents.get(i));
                    denomParensExponents.set(i, denomParensExponents.get(i) - 1);
                }

                topRightOutsideCoefficient = -oldNumOutsideCoefficient * denomOutsideCoefficient *
                        oldDenomParensExponents.get(oldDenomParensExponents.size() - 1);
                topRightOutsideExponent = oldNumOutsideExponent;
                topRightParensCoefficients = denomParensCoefficients;
                topRightParensExponents = denomParensExponents;
                topRightParensCoefficients2 = oldNumParensCoefficients;
                topRightParensExponents2 = oldNumParensExponents;
                topRightParensCoefficients3 = oldDenomParensCoefficients;
                topRightParensExponents3 = denomParensExponents2;
            }

            bottomOutsideCoefficient = oldDenomOutsideCoefficient * oldDenomOutsideCoefficient;
            bottomParensCoefficients = oldDenomParensCoefficients;
            bottomParensExponents = new ArrayList<>(oldDenomParensExponents);
            bottomParensExponents.set(bottomParensExponents.size() - 1,
                    bottomParensExponents.get(bottomParensExponents.size() - 1) * 2);
        }

        else if (denomExponent == 0 && denomParensCoefficients.size() != 0 && denomOutsideExponent != 0) {
            applyProductRule();
            if (numParensCoefficients.size() == 0) {
                topRightOutsideCoefficient = -denomOutsideCoefficient;
                topRightOutsideExponent = denomOutsideExponent;
                topRightParensCoefficients = oldDenomParensCoefficients;
                topRightParensExponents = oldDenomParensExponents;

                topRightOutsideCoefficient2 = -denomOutsideCoefficient2;
                topRightOutsideExponent2 = oldDenomOutsideExponent;
                topRightParensCoefficients2 = denomParensCoefficients;
                topRightParensExponents2 = denomParensExponents;
                topRightParensCoefficients3 = oldDenomParensCoefficients;
                topRightParensExponents3 = denomParensExponents2;
            }

            else if (numParensExponents.size() != 0 && numOutsideExponent == 0) {
                oldNumParensCoefficients = new ArrayList<>(numParensCoefficients);
                oldNumParensExponents = new ArrayList<>(numParensExponents);
                numParensExponents2 = new ArrayList<>(numParensExponents);
                oldNumOutsideCoefficient = numOutsideCoefficient;

                for (int i = 0; i < numParensCoefficients.size(); ++i) {
                    numParensCoefficients.set(i, numParensCoefficients.get(i) * numParensExponents.get(i));
                    numParensExponents.set(i, numParensExponents.get(i) - 1);
                }

                numParensExponents.set(numParensExponents.size() - 1, 1.0);
                numParensExponents2.set(numParensExponents2.size() - 1,
                        numParensExponents2.get(numParensExponents2.size() - 1) - 1);
                topLeftOutsideCoefficient = oldNumParensExponents.get(oldNumParensExponents.size() - 1)
                        * oldDenomOutsideCoefficient * numOutsideCoefficient;

                topLeftOutsideExponent = oldDenomOutsideExponent;
                topLeftParensCoefficients = numParensCoefficients;
                topLeftParensExponents = numParensExponents;
                topLeftParensCoefficients2 = oldNumParensCoefficients;
                topLeftParensExponents2 = numParensExponents2;
                topLeftParensCoefficients3 = oldDenomParensCoefficients;
                topLeftParensExponents3 = oldDenomParensExponents;

                topRightOutsideCoefficient = -denomOutsideCoefficient * oldNumOutsideCoefficient;
                topRightOutsideExponent = denomOutsideExponent;
                topRightParensCoefficients = oldDenomParensCoefficients;
                topRightParensExponents = oldDenomParensExponents;
                topRightParensCoefficients2 = oldNumParensCoefficients;
                topRightParensExponents2 = oldNumParensExponents;

                topRightOutsideCoefficient2 = -oldNumOutsideCoefficient * denomOutsideCoefficient2;
                topRightOutsideExponent2 = oldDenomOutsideExponent;
                topRightParensCoefficients3 = denomParensCoefficients;
                topRightParensExponents3 = denomParensExponents;
                topRightParensCoefficients4 = oldNumParensCoefficients;
                topRightParensExponents4 = oldNumParensExponents;
                topRightParensCoefficients5 = oldDenomParensCoefficients;
                topRightParensExponents5 = denomParensExponents2;
            }

            bottomOutsideCoefficient = oldDenomOutsideCoefficient * oldDenomOutsideCoefficient;
            bottomOutsideExponent = oldDenomOutsideExponent * 2;
            bottomParensCoefficients = oldDenomParensCoefficients;
            bottomParensExponents = new ArrayList<>(oldDenomParensExponents);
            bottomParensExponents.set(bottomParensExponents.size() - 1,
                    bottomParensExponents.get(bottomParensExponents.size() - 1) * 2);
        }

        else if (denomExponent != 0) {
            double oldDenomCoefficient = denomCoefficient;
            oldDenomExponent = denomExponent;
            if (numParensExponents.size() != 0 && numOutsideExponent == 0) {
                oldNumParensCoefficients = new ArrayList<>(numParensCoefficients);
                oldNumParensExponents = new ArrayList<>(numParensExponents);
                numParensExponents2 = new ArrayList<>(numParensExponents);

                for (int i = 0; i < numParensCoefficients.size(); ++i) {
                    numParensCoefficients.set(i, numParensCoefficients.get(i) * numParensExponents.get(i));
                    numParensExponents.set(i, numParensExponents.get(i) - 1);
                }
                numParensExponents2.set(numParensExponents2.size() - 1,
                        numParensExponents2.get(numParensExponents2.size() - 1) - 1);
                topLeftOutsideCoefficient = numParensExponents.get(numParensExponents.size() - 1)
                        * denomCoefficient * numOutsideCoefficient;
                topLeftOutsideExponent = denomExponent;
                topLeftParensCoefficients = numParensCoefficients;
                topLeftParensExponents = numParensExponents;
                topLeftParensCoefficients2 = oldNumParensCoefficients;
                topLeftParensExponents2 = numParensExponents2;

                denomCoefficient *= denomExponent;
                --denomExponent;

                topRightOutsideCoefficient = -numOutsideCoefficient * denomCoefficient;
                topRightOutsideExponent = denomExponent;
                topRightParensCoefficients = oldNumParensCoefficients;
                topRightParensExponents = oldNumParensExponents;
            }

            bottomCoefficient = oldDenomCoefficient * oldDenomCoefficient;
            bottomExponent = oldDenomExponent * 2;
        }
    }

    // toString() returns the function as a string
    public String toString() {
        results = new StringBuilder();
        df = new DecimalFormat("#.###");
        df.setRoundingMode(RoundingMode.CEILING);
        if (oldNumExponent == 0 && numParensExponents.size() == 0) {
            if (denomExponent == 0 && denomParensCoefficients.size() == 0) {
                results.append('0');
            }

            else if (denomParensCoefficients.size() != 0 && oldDenomOutsideExponent == 0) {
                if (topRightOutsideCoefficient != 1) {
                    results.append(df.format(topRightOutsideCoefficient));
                }

                writeInParentheses(topRightParensCoefficients, topRightParensExponents);


                if (topRightParensExponents2.get(topRightParensExponents2.size() - 1) != 0) {
                    writeInParentheses(topRightParensCoefficients2, topRightParensExponents2);

                    if (topRightParensExponents2.get(topRightParensExponents2.size() - 1) != 1) {
                        results.append('^');
                        results.append(df.format(topRightParensExponents2.get(topRightParensExponents2.size() - 1)));
                    }
                }

                if ("".equals(results.toString())) {
                    results.append('1');
                }

                results.append('/');
                if (bottomOutsideCoefficient != 0) {
                    results.append(df.format(bottomOutsideCoefficient));
                }

                writeInParentheses(bottomParensCoefficients, bottomParensExponents);
                if (bottomParensExponents.get(bottomParensExponents.size() - 1) != 1) {
                    results.append('^');
                    results.append(df.format(bottomParensExponents.get(bottomParensExponents.size() - 1)));
                }
            }

            else if (denomParensCoefficients.size() != 0 && oldDenomOutsideExponent != 0) {
                results.append('(');
                if (topRightOutsideCoefficient != 1) {
                    results.append(df.format(topRightOutsideCoefficient));
                }

                if (topRightOutsideExponent != 0) {
                    results.append('x');

                    if (topRightOutsideExponent != 1) {
                        results.append('^');
                        results.append(df.format(topRightOutsideExponent));
                    }
                }

                writeInParentheses(topRightParensCoefficients, topRightParensExponents);
                if (topRightParensExponents.get(topRightParensExponents.size() - 1) != 1) {
                    results.append('^');
                    results.append(df.format(topRightParensExponents.get(topRightParensExponents.size() - 1)));
                }

                if (topRightOutsideCoefficient2 > 0) {
                    results.append('+');
                }

                if (topRightOutsideCoefficient2 != 1) {
                    results.append(df.format(topRightOutsideCoefficient2));
                }

                results.append("x");
                if (topRightOutsideExponent2 != 1) {
                    results.append(df.format(topRightOutsideExponent2));
                }

                writeInParentheses(topRightParensCoefficients2, topRightParensExponents2);
                if (topRightParensExponents3.get(topRightParensExponents3.size() - 1) != 0) {
                    writeInParentheses(topRightParensCoefficients3, topRightParensExponents3);

                    if (topRightParensExponents3.get(topRightParensExponents3.size() - 1) != 1) {
                        results.append('^');
                        results.append(df.format(topRightParensExponents3.size() - 1));
                    }
                }

                results.append(")/");
                if (bottomOutsideCoefficient != 1) {
                    results.append(df.format(bottomOutsideCoefficient));
                }

                results.append("x^");
                results.append(df.format(bottomOutsideExponent));
                writeInParentheses(bottomParensCoefficients, bottomParensExponents);
                results.append("^");
                results.append(df.format(bottomParensExponents.get(bottomParensExponents.size() - 1)));
            }

            else if (denomExponent != 0) {
                results.append("1/");
                results.append(df.format(denomCoefficient));
                results.append("x^");
                results.append(df.format(denomExponent));
            }
        }

        else if (oldNumExponent != 0) {
            if (denomExponent == 0 && denomParensCoefficients.size() == 0) {
                if (numCoefficient != 1) {
                    results.append(df.format(numCoefficient));
                }

                if (numExponent != 0) {
                    results.append('x');

                    if (numExponent != 1) {
                        results.append('^');
                        results.append(df.format(numExponent));
                    }
                }

                if ("".equals(results.toString())) {
                    results.append("1");
                }
            }

            else if (denomParensExponents.size() != 0 && denomOutsideExponent == 0) {
                results.append("(");

                if (topLeftOutsideCoefficient != 1) {
                    results.append(df.format(topLeftOutsideCoefficient));
                }

                if (topLeftOutsideExponent != 0) {
                    results.append('x');

                    if (topLeftOutsideExponent != 1) {
                        results.append('^');
                        results.append(df.format(topLeftOutsideExponent));
                    }
                }

                writeInParentheses(topLeftParensCoefficients, topLeftParensExponents);
                if (topLeftParensExponents.get(topLeftParensExponents.size() - 1) != 1) {
                    results.append('^');
                    results.append(df.format(topLeftParensExponents.get(topLeftParensExponents.size() - 1)));
                }

                if (topRightOutsideCoefficient > 0) {
                    results.append('+');
                }

                if (topRightOutsideCoefficient != 1) {
                    results.append(df.format(topRightOutsideCoefficient));
                }

                results.append('x');
                if (topRightOutsideExponent != 1) {
                    results.append('^');
                    results.append(df.format(topRightOutsideExponent));
                }

                writeInParentheses(topRightParensCoefficients, topRightParensExponents);
                if (topRightParensExponents2.get(topRightParensExponents2.size() - 1) != 0) {
                    writeInParentheses(topRightParensCoefficients2, topRightParensExponents2);

                    if (topRightParensExponents2.get(topRightParensExponents2.size() - 1) != 1) {
                        results.append('^');
                        results.append(df.format(topRightParensExponents2.get(topRightParensExponents2.size() - 1)));
                    }
                } results.append(")/");
                if (bottomOutsideCoefficient != 1) {
                    results.append(df.format(bottomOutsideCoefficient));
                }

                writeInParentheses(bottomParensCoefficients, bottomParensExponents);
                results.append('^');
                results.append(df.format(bottomParensExponents.get(bottomParensExponents.size() - 1)));
            }
        }

        else if (numParensExponents.size() != 0 && oldNumOutsideExponent == 0) {
            if (oldDenomExponent == 0 && denomParensCoefficients.size() == 0) {
                if (topLeftOutsideCoefficient != 1) {
                    results.append(df.format(topLeftOutsideCoefficient));
                }

                writeInParentheses(topLeftParensCoefficients, topLeftParensExponents);
                if (topLeftParensExponents2.get(topLeftParensExponents2.size() - 1) != 0) {
                    writeInParentheses(topLeftParensCoefficients2, topLeftParensExponents2);

                    if (topLeftParensExponents2.get(topLeftParensExponents2.size() - 1) != 1) {
                        results.append('^');
                        results.append(df.format(topLeftParensExponents2.get(topLeftParensExponents2.size() - 1)));
                    }
                }
            } else if (oldDenomExponent != 0) {
                results.append("(");
                if (topLeftOutsideCoefficient != 1) {
                    results.append(df.format(topLeftOutsideCoefficient));
                }

                results.append('x');
                if (topLeftOutsideExponent != 1) {
                    results.append('^');
                    results.append(df.format(topLeftOutsideExponent));
                }

                writeInParentheses(topLeftParensCoefficients, topLeftParensExponents);
                if (topLeftParensExponents2.get(topLeftParensExponents2.size() - 1) != 0) {
                    writeInParentheses(topLeftParensCoefficients2, topLeftParensExponents2);

                    if (topLeftParensExponents2.get(topLeftParensExponents2.size() - 1) != 1) {
                        results.append('^');
                        results.append(df.format(topLeftParensExponents2.get(topLeftParensExponents2.size() - 1)));
                    }
                }

                if (topRightOutsideCoefficient > 0) {
                    results.append('+');
                }

                if (topRightOutsideCoefficient != 1) {
                    results.append(df.format(topRightOutsideCoefficient));
                }

                if (topRightOutsideExponent != 0) {
                    results.append('x');

                    if (topRightOutsideExponent != 1) {
                        results.append(df.format(topRightOutsideExponent));
                    }
                }

                writeInParentheses(topRightParensCoefficients, topRightParensExponents);
                if (topRightParensExponents.get(topRightParensExponents.size() - 1) != 1) {
                    results.append('^');
                    results.append(df.format(topRightParensExponents.get(topRightParensExponents.size() - 1)));
                }

                results.append(")/");
                if (bottomCoefficient != 1) {
                    results.append(df.format(bottomCoefficient));
                }

                results.append("x^");
                results.append(df.format(bottomExponent));
            }

            else if (denomParensExponents.size() != 0 && oldDenomOutsideExponent == 0) {
                results.append('(');
                if (topLeftOutsideCoefficient != 1) {
                    results.append(df.format(topLeftOutsideCoefficient));
                }

                writeInParentheses(topLeftParensCoefficients, topLeftParensExponents);
                writeInParentheses(topLeftParensCoefficients2, topLeftParensExponents2);
                if (topLeftParensExponents2.get(topLeftParensExponents2.size() - 1) != 0){
                    if (topLeftParensExponents2.get(topLeftParensExponents2.size() - 1) != 1) {
                        results.append('^');
                        results.append(df.format(topLeftParensExponents2.get(topLeftParensExponents2.size() - 1)));
                    }

                    writeInParentheses(topLeftParensCoefficients3, topLeftParensExponents3);
                    if (topLeftParensExponents3.get(topLeftParensExponents3.size() - 1) != 1) {
                        results.append('^');
                        results.append(df.format(topLeftParensExponents3.get(topLeftParensExponents3.size() - 1)));
                    }
                }

                if (topRightOutsideCoefficient > 0) {
                    results.append('+');
                }

                if (topRightOutsideCoefficient != 1) {
                    results.append(df.format(topRightOutsideCoefficient));
                }

                writeInParentheses(topRightParensCoefficients, topRightParensExponents);
                writeInParentheses(topRightParensCoefficients2, topRightParensExponents2);
                if (topRightParensExponents2.get(topRightParensExponents2.size() - 1) != 1) {
                    results.append('^');
                    results.append(df.format(topRightParensExponents2.get(topRightParensExponents2.size() - 1)));
                }

                if (topRightParensExponents3.get(topRightParensExponents3.size() - 1) != 0) {
                    writeInParentheses(topRightParensCoefficients3, topRightParensExponents3);

                    if (topRightParensExponents3.get(topRightParensExponents3.size() - 1) != 1) {
                        results.append('^');
                        results.append(df.format(topRightParensExponents3.get(topRightParensExponents3.size() - 1)));
                    }
                }

                results.append(")/");
                if (bottomOutsideCoefficient != 1) {
                    results.append(df.format(bottomOutsideCoefficient));
                }

                writeInParentheses(bottomParensCoefficients, bottomParensExponents);
                results.append('^');
                results.append(df.format(bottomParensExponents.get(bottomParensExponents.size() - 1)));
            }

            else if (denomParensExponents.size() != 0 && oldDenomOutsideExponent != 0) {
                results.append('(');
                if (topLeftOutsideCoefficient != 1) {
                    results.append(df.format(topLeftOutsideCoefficient));
                }

                results.append('x');
                if (topLeftOutsideExponent != 1) {
                    results.append(df.format(topLeftOutsideExponent));
                }

                writeInParentheses(topLeftParensCoefficients, topLeftParensExponents);
                if (topLeftParensExponents.get(topLeftParensExponents.size() - 1) != 1) {
                    results.append('^');
                    results.append(df.format(topLeftParensExponents.get(topLeftParensExponents.size() - 1)));
                }

                if (topLeftParensExponents2.get(topLeftParensExponents2.size() - 1) != 0) {
                    writeInParentheses(topLeftParensCoefficients2, topLeftParensExponents2);

                    if (topLeftParensExponents2.get(topLeftParensExponents2.size() - 1) != 1) {
                        results.append('^');
                        results.append(df.format(topLeftParensExponents2.get(topLeftParensExponents2.size() - 1)));
                    }
                }

                writeInParentheses(topLeftParensCoefficients3, topLeftParensExponents3);
                if (topLeftParensExponents3.get(topLeftParensExponents3.size() - 1) != 1) {
                    results.append('^');
                    results.append(df.format(topLeftParensExponents3.get(topLeftParensExponents3.size() - 1)));
                }

                if (topRightOutsideCoefficient > 0) {
                    results.append('+');
                }

                if (topRightOutsideCoefficient != 1) {
                    results.append(df.format(topRightOutsideCoefficient));
                }

                if (topRightOutsideExponent != 0) {
                    results.append('x');

                    if (topRightOutsideExponent != 1) {
                        results.append('^');
                        results.append(df.format(topRightOutsideExponent));
                    }
                }

                writeInParentheses(topRightParensCoefficients, topRightParensExponents);
                if (topRightParensExponents.get(topRightParensExponents.size() - 1) != 1) {
                    results.append('^');
                    results.append(df.format(topRightParensExponents.get(topRightParensExponents.size() - 1)));
                }

                writeInParentheses(topRightParensCoefficients2, topRightParensExponents2);
                if (topRightParensExponents2.get(topRightParensExponents2.size() - 1) != 1) {
                    results.append('^');
                    results.append(df.format(topRightParensExponents2.get(topRightParensExponents2.size() - 1)));
                }

                if (topRightOutsideCoefficient2 > 0) {
                    results.append('+');
                }

                if (topRightOutsideCoefficient2 != 1) {
                    results.append(df.format(topRightOutsideCoefficient2));
                }

                results.append('x');
                if (topRightOutsideExponent2 != 1) {
                    results.append('^');
                    results.append(df.format(topRightOutsideExponent2));
                }

                writeInParentheses(topRightParensCoefficients3, topRightParensExponents3);
                writeInParentheses(topRightParensCoefficients4, topRightParensExponents4);
                if (topRightParensExponents4.get(topRightParensExponents4.size() - 1) != 1) {
                    results.append('^');
                    results.append(df.format(topRightParensExponents4.get(topRightParensExponents4.size() - 1)));
                }

                if (topRightParensExponents5.get(topRightParensExponents5.size() - 1) != 0) {
                    writeInParentheses(topRightParensCoefficients5, topRightParensExponents5);

                    if (topRightParensExponents5.get(topRightParensExponents5.size() - 1) != 1) {
                        results.append('^');
                        results.append(df.format(topRightParensExponents.get(topRightParensExponents.size() - 1)));
                    }
                }

                results.append(")/");
                if (bottomOutsideCoefficient != 0) {
                    results.append(df.format(bottomOutsideCoefficient));
                }

                results.append("x^");
                results.append(df.format(bottomOutsideExponent));
                writeInParentheses(bottomParensCoefficients, bottomParensExponents);
                results.append('^');
                results.append(df.format(bottomParensExponents.get(bottomParensExponents.size() - 1)));
            }
        }

        else if (oldNumExponent == 0 && numParensExponents.size() != 0 && oldNumOutsideExponent != 0) {
            if (oldDenomExponent == 0 & denomParensCoefficients.size() == 0) {
                if (topLeftOutsideCoefficient != 1) {
                    results.append(df.format(topLeftOutsideCoefficient));
                }

                if (topLeftOutsideExponent != 0) {
                    results.append('x');

                    if (topLeftOutsideExponent != 1) {
                        results.append('^');
                        results.append(df.format(topLeftOutsideExponent));
                    }
                }

                writeInParentheses(topLeftParensCoefficients, topLeftParensExponents);
                if (topLeftParensExponents.get(topLeftParensExponents.size() - 1) != 1) {
                    results.append('^');
                    results.append(df.format(topLeftParensExponents.get(topLeftParensExponents.size() - 1)));
                }

                if (topRightOutsideCoefficient > 0) {
                    results.append('+');
                }

                if (topRightOutsideCoefficient != 1) {
                    results.append(df.format(topRightOutsideCoefficient));
                }

                if (topRightOutsideExponent != 0) {
                    results.append('x');

                    if (topRightOutsideExponent != 1) {
                        results.append('^');
                        results.append(df.format(topRightOutsideExponent));
                    }
                }

                writeInParentheses(topRightParensCoefficients, topRightParensExponents);
                if (topRightParensExponents2.get(topRightParensExponents2.size() - 1) != 0) {
                    writeInParentheses(topRightParensCoefficients2, topRightParensExponents2);

                    if (topRightParensExponents2.get(topRightParensExponents2.size() - 1) != 1) {
                        results.append('^');
                        results.append(df.format(topRightParensExponents2.get(topRightParensExponents2.size() - 1)));
                    }
                }
            }

            else if (denomParensCoefficients.size() != 0 && oldDenomOutsideExponent == 0){
                results.append('(');
                if (topLeftOutsideCoefficient != 1) {
                    results.append(df.format(topLeftOutsideCoefficient));
                }

                if (topLeftOutsideExponent != 0) {
                    results.append('x');

                    if (topLeftOutsideExponent != 1) {
                        results.append('^');
                        results.append(df.format(topLeftOutsideExponent));
                    }
                }

                writeInParentheses(topLeftParensCoefficients, topLeftParensExponents);
                if (topLeftParensExponents.get(topLeftParensExponents.size() - 1) != 1) {
                    results.append('^');
                    results.append(df.format(topLeftParensExponents.get(topLeftParensExponents.size() - 1)));
                }

                writeInParentheses(topLeftParensCoefficients2, topLeftParensExponents2);
                if (topLeftParensExponents2.get(topLeftParensExponents2.size() - 1) != 1) {
                    results.append('^');
                    results.append(df.format(topLeftParensExponents2.get(topLeftParensExponents2.size() - 1)));
                }

                if (topLeftOutsideCoefficient2 > 0) {
                    results.append('+');
                }

                if (topLeftOutsideCoefficient2 != 1) {
                    results.append(df.format(topLeftOutsideCoefficient2));
                }

                results.append('x');
                if (topLeftOutsideExponent2 != 1) {
                    results.append(df.format(topLeftOutsideExponent2));
                }

                writeInParentheses(topLeftParensCoefficients3, topLeftParensExponents3);
                writeInParentheses(topLeftParensCoefficients4, topLeftParensExponents4);
                if (topLeftParensExponents4.get(topLeftParensExponents4.size() - 1) != 1) {
                    results.append('^');
                    results.append(df.format(topLeftParensExponents4.get(topLeftParensExponents4.size() - 1)));
                }

                if (topLeftParensExponents5.get(topLeftParensExponents5.size() - 1) != 0) {
                    writeInParentheses(topLeftParensCoefficients5, topLeftParensExponents5);

                    if (topLeftParensExponents5.get(topLeftParensExponents5.size() - 1) != 1) {
                        results.append('^');
                        results.append(df.format(topLeftParensExponents5.get(topLeftParensExponents5.size() - 1)));
                    }
                }

                if (topRightOutsideCoefficient > 0) {
                    results.append('+');
                }

                if (topRightOutsideCoefficient != 1) {
                    results.append(df.format(topRightOutsideCoefficient));
                }

                results.append('x');
                if (topRightOutsideExponent != 1) {
                    results.append('^');
                    results.append(df.format(topRightOutsideExponent));
                }

                writeInParentheses(topRightParensCoefficients, topRightParensExponents);
                writeInParentheses(topRightParensCoefficients2, topRightParensExponents2);
                if (topRightParensExponents2.get(topRightParensExponents2.size() - 1) != 1) {
                    results.append('^');
                    results.append(df.format(topRightParensExponents2.get(topRightParensExponents2.size() - 1)));
                }

                if (topRightParensExponents3.get(topRightParensExponents3.size() - 1) != 0) {
                    writeInParentheses(topRightParensCoefficients3, topRightParensExponents3);

                    if (topRightParensExponents3.get(topRightParensExponents3.size() - 1) != 1) {
                        results.append('^');
                        results.append(df.format(topRightParensExponents3.get(topRightParensExponents3.size() - 1)));
                    }
                }

                results.append(")/");
                if (bottomOutsideCoefficient != 1) {
                    results.append(df.format(bottomOutsideCoefficient));
                }

                writeInParentheses(bottomParensCoefficients, bottomParensExponents);
                results.append('^');
                results.append(df.format(bottomParensExponents.get(bottomParensExponents.size() - 1)));
            }
        }
        return results.toString();
    }

    // writeInParentheses() converts the terms in a set of parentheses into a readable form
    private void writeInParentheses(ArrayList<Double> coefficients, ArrayList<Double> exponents) {
        results.append('(');
        for (int i = 0; i < coefficients.size(); ++i) {
            if (coefficients.get(i) != 0) {
                results.append(df.format(coefficients.get(i)));

                if (exponents.get(i) != 0) {
                    results.append('x');

                    if (exponents.get(i) != 1) {
                        results.append('^');
                        results.append(df.format(exponents.get(i)));
                    }
                }
                if (i != coefficients.size() - 1) {
                    if (coefficients.get(i + 1) > 0) {
                        results.append('+');
                    }
                }
            }
        }

        results.append(')');
    }
}
