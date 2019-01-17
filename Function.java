import javax.swing.*;
import java.util.ArrayList;

public class Function {
    private String [] numDenom;
    private ArrayList<String> numTerms, denomTerms;
    private char [] validChars = new char [] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'x',
            '*', '-', '(', ')', '+', '^', '/', '.'};
    private int divideCount, openParensCount, closedParensCount;
    private boolean numIsBeingMultiplied, denomIsBeingMultiplied;
    private boolean valid = true;
    protected ArrayList<Double> numParensCoefficients, numParensExponents, denomParensCoefficients, denomParensExponents;
    protected double numCoefficient, numExponent, denomCoefficient,
            denomExponent, numOutsideCoefficient, denomOutsideCoefficient,
            numOutsideExponent, denomOutsideExponent;

    protected Function(String function) {
        function = function.replaceAll(" ", "").toLowerCase();
        if (isValid(function)) {
            try {
                carve(function);
                separateTerms();
                readTerms();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Invalid input.");
                valid = false;
            }
        }
    }

    // IsValid() checks for basic validity and helps users who want to type a legitimate function
    private boolean isValid(String f) {
        for (int i = 0; i < f.length(); ++i) {
            if (f.charAt(i) == '/')
                divideCount++;
            if (divideCount > 1) {
                valid = false;
                JOptionPane.showMessageDialog(null, "Invalid input: complex fractions not supported.");
                return valid;
            }

            if (f.charAt(i) == '(') {
                openParensCount++;
            } if (f.charAt(i) == ')') {
                closedParensCount++;
            }

            boolean usesValidChars = false;
            for (int j = 0; j < validChars.length; j++) {
                if (f.charAt(i) == validChars[j]) {
                    usesValidChars = true;
                    break;
                }
            } if (!usesValidChars) {
                valid = false;
                JOptionPane.showMessageDialog(null, "Invalid input: must be in terms of x.");
                return valid;
            }
        }
        if (openParensCount != closedParensCount) {
            valid = false;
            JOptionPane.showMessageDialog(null, "Invalid input: unequal amount of parentheses.");
            return valid;
        }

        return valid;
    }

    // carve() splits the function into numerator and denominator
    private void carve(String f) {
        if (divideCount > 0)
            numDenom = f.split("/");
        else {
            numDenom = new String [] {f, "1"};
        }
    }

    // seperateTerms() breaks up the function into terms so that it can read them in readTerms()
    private void separateTerms() {
        numTerms = new ArrayList<>();
        denomTerms = new ArrayList<>();

        for (int h = 0; h < 2; h++) {
            String f = numDenom[h];
            int parensLength = 0;
            int continueCount = -1;
            for (int i = 0; i < f.length(); ++i) {
                if (continueCount >= i) {
                    continue;
                } if (f.charAt(i) == '(') {
                    int multiplyingChars = 0;
                    if (i > 0) {
                        for (int j = 1; j <= i; j++) {
                            for (int k = 0; k < 12; k++) {
                                if (f.charAt(i - j) == validChars[k]) {
                                    if (h == 0) {
                                        numIsBeingMultiplied = true;
                                    } else {
                                        denomIsBeingMultiplied = true;
                                    }
                                    break;
                                }
                            } if ((h == 0 && !numIsBeingMultiplied) || (h == 1 && !denomIsBeingMultiplied)) {
                                break;
                            } else {
                                multiplyingChars++;
                            }
                        }
                    }
                    for (int j = i; j < f.length(); j++) {
                        if (f.charAt(j) == ')') {
                            parensLength = j;
                            break;
                        }
                    }

                    if (f.length() - 1 > parensLength) {
                        if (f.charAt(parensLength + 1) == '^') {
                            for (int j = parensLength + 1; j < f.length(); j++) {
                                if (f.charAt(j) == '+' || f.charAt(j) == '-') {
                                    break;
                                }
                                parensLength = j;
                            }
                        }
                    }

                    if (h == 0) {
                        numTerms.add(f.substring(i - multiplyingChars, parensLength + 1));
                        break;
                    } else {
                        denomTerms.add(f.substring(i - multiplyingChars, parensLength + 1));
                        break;
                    }
                }

                else {
                    boolean precedesParens = false;
                    for (int j = i; j < f.length(); j++) {
                        if (f.charAt(j) == '(') {
                            precedesParens = true;
                            continueCount = j - 1;
                            break;
                        }
                    }
                    if (!precedesParens && h == 0) {
                        numTerms.add(f);
                    } else if (!precedesParens) {
                        denomTerms.add(f);
                    }
                }
            }
        }
    }

    // readTerms() parses the function by breaking down each term
    private void readTerms() {
        numParensCoefficients = new ArrayList<>();
        numParensExponents = new ArrayList<>();
        denomParensCoefficients = new ArrayList<>();
        denomParensExponents = new ArrayList<>();

        for (int h = 0; h < 2; h++) {
            ArrayList<String> f;
            if (h == 0) {
                f = numTerms;
            } else {
                f = denomTerms;
            }
            for (String term : f) {
                if (term.contains("(")) {
                    double parensExponent = 1;
                    int index = 0;
                    String multiplyingTerm = null;
                    ArrayList<String> parensTerms = new ArrayList<>();

                    if ((h == 0 && numIsBeingMultiplied) || (h == 1 && denomIsBeingMultiplied)) {
                        multiplyingTerm = term.substring(0, term.indexOf('('));
                        boolean hasX = false;
                        for (int i = 0; i < multiplyingTerm.length(); ++i) {
                            if (multiplyingTerm.charAt(i) == 'x' && i == 0 && h == 0) {
                                hasX = true;
                                numOutsideCoefficient = 1;
                            }

                            else if (multiplyingTerm.charAt(i) == 'x' && i > 0 && h == 0) {
                                hasX = true;
                                numOutsideCoefficient = Double.parseDouble(multiplyingTerm.substring(0, i));
                            }

                            else if (multiplyingTerm.charAt(i) == 'x' && i == 0 && h == 1) {
                                hasX = true;
                                denomOutsideCoefficient = 1;
                            }

                            else if (multiplyingTerm.charAt(i) == 'x' && i > 0 && h == 1) {
                                hasX = true;
                                denomOutsideCoefficient = Double.parseDouble(multiplyingTerm.substring(0, i));
                            }

                            if (multiplyingTerm.charAt(i) == 'x' && i + 1 == multiplyingTerm.length()) {
                                if (h == 0) {
                                    numOutsideExponent = 1;
                                } else {
                                    denomOutsideExponent = 1;
                                }
                            }

                            else if (multiplyingTerm.charAt(i) == 'x' && i + 1 < multiplyingTerm.length()) {
                                if (h == 0) {
                                    numOutsideExponent = Double.parseDouble(multiplyingTerm.substring(i + 2));
                                } else {
                                    denomOutsideExponent = Double.parseDouble(multiplyingTerm.substring(i + 2));
                                }
                            }
                        } if (!hasX && h == 0) {
                            numOutsideExponent = 0;
                            numOutsideCoefficient = Double.parseDouble(multiplyingTerm);
                        } else if (!hasX) {
                            denomOutsideExponent = 0;
                            denomOutsideCoefficient = Double.parseDouble(multiplyingTerm);
                        }
                    } else {
                        if (h == 0) {
                            numOutsideCoefficient = 1;
                            numOutsideExponent = 0;
                        } else {
                            denomOutsideCoefficient = 1;
                            denomOutsideExponent = 0;
                        }
                    }
                    if (term.indexOf(')') + 1 < term.length()) {
                        parensExponent = Double.parseDouble(String.valueOf(term.substring(term.indexOf(')') + 2)));
                    } if (multiplyingTerm != null) {
                        term = term.substring(multiplyingTerm.length(), term.indexOf(')') + 1);
                    } else {
                        term = term.substring(0, term.indexOf(')') + 1);
                    }
                    term = term.replaceAll("[()]", "");
                    for (int i = 0; i < term.length(); ++i) {
                        boolean nextTermIsNegative = false;
                        if ((term.charAt(i) == '+' || term.charAt(i) == '-') && i > 0) {
                            if (term.charAt(i) == '-') {
                                nextTermIsNegative = true;
                            } parensTerms.add(term.substring(index, i));
                            if (nextTermIsNegative) {
                                index = i;
                            } else {
                                index = i + 1;
                            }
                        } if (i == term.length() - 1) {
                            parensTerms.add(term.substring(index));
                        }
                    }
                    for (String parenTerm : parensTerms) {
                        boolean hasX = false;
                        for (int i = 0; i < parenTerm.length(); ++i) {
                            if (parenTerm.charAt(i) == 'x' && i == 0 && h == 0) {
                                hasX = true;
                                numParensCoefficients.add(1.0);
                            }

                            else if (parenTerm.charAt(i) == 'x' && i > 0 && h == 0) {
                                hasX = true;
                                try {
                                    numParensCoefficients.add(Double.parseDouble(parenTerm.substring(0, i)));
                                } catch (NumberFormatException e) {
                                    numParensCoefficients.add(-1.0);
                                }
                            }

                            else if (parenTerm.charAt(i) == 'x' && i == 0 && h == 1) {
                                hasX = true;
                                denomParensCoefficients.add(1.0);
                            }

                            else if (parenTerm.charAt(i) == 'x' && i > 0 && h == 1) {
                                hasX = true;
                                try {
                                    denomParensCoefficients.add(Double.parseDouble(parenTerm.substring(0, i)));
                                } catch (NumberFormatException e) {
                                    denomParensCoefficients.add(-1.0);
                                }
                            }

                            if (parenTerm.charAt(i) == 'x' && i + 1 == parenTerm.length()) {
                                if (h == 0) {
                                    numParensExponents.add(1.0);
                                } else {
                                    denomParensExponents.add(1.0);
                                }
                            }

                            else if (parenTerm.charAt(i) == 'x' && i + 1 < parenTerm.length()) {
                                if (h == 0) {
                                    numParensExponents.add(Double.parseDouble(parenTerm.substring(i + 2)));
                                } else {
                                    denomParensExponents.add(Double.parseDouble(parenTerm.substring(i + 2)));
                                }
                            }
                        } if (!hasX && h == 0) {
                            numParensCoefficients.add(Double.parseDouble(parenTerm));
                            numParensExponents.add(0.0);
                        } else if (!hasX) {
                            denomParensCoefficients.add(Double.parseDouble(parenTerm));
                            denomParensExponents.add(0.0);
                        }
                    } if (h == 0) {
                        numParensExponents.add(parensExponent);
                    } else {
                        denomParensExponents.add(parensExponent);
                    }
                }
                else {
                    boolean hasX = false;
                    for (int i = 0; i < term.length(); ++i) {
                        if (term.charAt(i) == 'x' && i == 0 && h == 0) {
                            hasX = true;
                            numCoefficient = 1;
                        }

                        else if (term.charAt(i) == 'x' && i > 0 && h == 0) {
                            hasX = true;
                            try {
                                numCoefficient = Double.parseDouble(term.substring(0, i));
                            } catch (NumberFormatException e) {
                                numCoefficient = -1;
                            }
                        }

                        else if (term.charAt(i) == 'x' && i == 0 && h == 1) {
                            hasX = true;
                            denomCoefficient = 1;
                        }

                        else if (term.charAt(i) == 'x' && i > 0 && h == 1) {
                            hasX = true;
                            try {
                                denomCoefficient = Double.parseDouble(term.substring(0, i));
                            } catch (NumberFormatException e) {
                                denomCoefficient = -1;
                            }
                        }

                        if (term.charAt(i) == 'x' && i + 1 == term.length()) {
                            if (h == 0) {
                                numExponent = 1;
                            } else {
                                denomExponent = 1;
                            }
                        }

                        else if (term.charAt(i) == 'x' && i + 1 < term.length()) {
                            if (h == 0) {
                                numExponent = Double.parseDouble(term.substring(i + 2));
                            } else {
                                denomExponent = Double.parseDouble(term.substring(i + 2));
                            }
                        }
                    }
                    if (!hasX && h == 0) {
                        numExponent = 0;
                        numCoefficient = 1;
                    } else if (!hasX) {
                        denomExponent = 0;
                        denomCoefficient = 1;
                    }
                }
            }
        }
    }

    public boolean isValid() {
        return valid;
    }

}



































