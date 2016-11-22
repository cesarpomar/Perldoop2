package perldoop;

import perldoop.lib.*;

public class Test {
  private static String flag;
  private static String Border;
  private static String DepLex;
  private static String b2;
  private static String a2;
  private static String l;
  private static String r;
  private static String ADJ;
  private static String NOUN;
  private static String PRP;
  private static String ADV;
  private static String CARD;
  private static String CONJ;
  private static String DET;
  private static String PRO;
  private static String VERB;
  private static String I;
  private static String DATE;
  private static String POS;
  private static String PCLE;
  private static String EX;
  private static String Fc;
  private static String Fg;
  private static String Fz;
  private static String Fe;
  private static String Fd;
  private static String Fx;
  private static String Fpa;
  private static String Fpt;
  private static String SENT;
  private static String NOMINAL;
  private static String NOUNCOORD;
  private static String NOUNSINGLE;
  private static String NPCOORD;
  private static String X;
  private static String NOTVERB;
  private static String PUNCT;
  private static String Quant;
  private static String Partitive;
  private static String PrepLocs;
  private static String PrepRA;
  private static String PrepMA;
  private static String cliticopers;
  private static String PROperssuj;
  private static String PROsujgz;
  private static String VModalEN;
  private static String Vpass;
  private static String Vaux;
  private static String NincSp;
  private static String NincExp;
  private static String PTa;
  private static Integer i;
  private static Integer listTags;
  private static Integer seq;
  private static Integer CountLines;
  private static String info;
  private static PerlList<String> Token;
  private static PerlList<String> Tag;
  private static PerlList<String> Lemma;
  private static PerlList<String> Attributes;
  private static PerlList<PerlMap<String>> ATTR;
  private static PerlMap<String> ATTR_lemma;
  private static PerlMap<Integer> TagStr;
  private static PerlMap<String> IDF;
  private static PerlMap<Integer> Ordenar;
  private static Integer j;

  public static Box[] ReConvertChar(Box[] __) {
    String x = Casting.toString(__[0]);
    String y = Casting.toString(__[1]);
    String z = Casting.toString(__[2]);
    Attributes.set(
        Casting.toInteger(z),
        Regex.substitution(
            Attributes.get(Casting.toInteger(z)), "lemma:\\*" + y + "\\*", "lemma:" + x, "", true));
    Attributes.set(
        Casting.toInteger(z),
        Regex.substitution(
            Attributes.get(Casting.toInteger(z)), "token:\\*" + y + "\\*", "token:" + x, "", true));
    ATTR.get(Casting.toInteger(z))
        .put(
            "lemma",
            Regex.substitution(
                ATTR.get(Casting.toInteger(z)).get("lemma"), "\\*" + y + "\\*", x, "", true));
    ATTR.get(Casting.toInteger(z))
        .put(
            "token",
            Regex.substitution(
                ATTR.get(Casting.toInteger(z)).get("token"), "\\*" + y + "\\*", x, "", true));
    Token.set(
        Casting.toInteger(z),
        Regex.substitution(Token.get(Casting.toInteger(z)), "\\*" + y + "\\*", x, "", true));
    Lemma.set(
        Casting.toInteger(z),
        Regex.substitution(Lemma.get(Casting.toInteger(z)), "\\*" + y + "\\*", x, "", true));
    return new Box[0];
  }

  public static Box[] HeadDep(Box[] __) {
    String y;
    String z;
    String[] x;
    y = Casting.toString(__[0]);
    z = Casting.toString(__[1]);
    x =
        (String[])
            Casting.cast(
                new Casting() {
                  @Override
                  public Object casting(Object arg) {
                    if (arg == null) {
                      return null;
                    }
                    Box[] col = (Box[]) arg;
                    String[] col2 = new String[col.length];
                    for (int i = 0; i < col.length; i++) {
                      col2[i] = Casting.toString(col[i]);
                    }
                    return col2;
                  }
                },
                Pd.subEquals(2, __));
    Integer n1 = 0;
    Integer n2 = 0;
    Boolean found = false;
    String[] pd_1z = Perl.split(",", z);
    for (Integer m = 0; Pd.checkNull(m) <= Pd.checkNull((x.length - 1)); m = Pd.checkNull(m) + 1) {
      String Head = x[m];
      m = Pd.checkNull(m) + 1;
      String Dep = x[m];
      String[] pd_18;
      pd_18 = Regex.matcher(Head, "[w]+_([0-9]+)", "", false);
      n1 = Casting.toInteger(pd_18[0]);
      String[] pd_19;
      pd_19 = Regex.matcher(Dep, "[w]+_([0-9]+)", "", false);
      n2 = Casting.toInteger(pd_19[0]);
      if (Pd.compare(Pd.checkNull(z), "") == 0) {
        Head = Lemma.get(n1) + "_" + Tag.get(n1) + "_" + n1;
        String Rel = y;
        Dep = Lemma.get(n2) + "_" + Tag.get(n2) + "_" + n2;
        Ordenar.put("(" + Rel + ";" + Head + ";" + Dep + ")", n2);
      } else {
        for (String atr : pd_1z) {
          if ((Pd.compare(Pd.checkNull(ATTR.get(n1).get(atr)), Pd.checkNull(ATTR.get(n2).get(atr)))
                  != 0)
              && (!Regex.match(ATTR.get(n1).get(atr), "^[NC0]$", "", false)
                  && !Regex.match(ATTR.get(n2).get(atr), "^[NC0]$", "", false))
              && /*#a modificar: so no caso de que atr = number or genre (N = invariable or neutral)*/ (Pd
                          .compare(Pd.checkNull(ATTR.get(n1).get(atr)), "")
                      != 0
                  && Pd.compare(Pd.checkNull(ATTR.get(n2).get(atr)), "") != 0)) {
            found = true;
          }
        }
        /* print STDERR "Found: $found\n";*/
        if (found) {
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + Tag.get(n1) + "_" + n1 + "_<)",
                      "$1concord:0\\|",
                      "",
                      false));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + Tag.get(n2) + "_" + n2 + "_<)",
                      "$1concord:0\\|",
                      "",
                      false));
          found = false;
        } else {
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + Tag.get(n1) + "_" + n1 + "_<)",
                      "$1concord:1\\|",
                      "",
                      false));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + Tag.get(n2) + "_" + n2 + "_<)",
                      "$1concord:1\\|",
                      "",
                      false));
          found = false;
          Head = Lemma.get(n1) + "_" + Tag.get(n1) + "_" + n1;
          String Rel = y;
          Dep = Lemma.get(n2) + "_" + Tag.get(n2) + "_" + n2;
          Ordenar.put("(" + Rel + ";" + Head + ";" + Dep + ")", n2);
        }
      }
    }
    return new Box[0];
  }

  public static Box[] DepHead(Box[] __) {
    String y;
    String z;
    String[] x;
    y = Casting.toString(__[0]);
    z = Casting.toString(__[1]);
    x =
        (String[])
            Casting.cast(
                new Casting() {
                  @Override
                  public Object casting(Object arg) {
                    if (arg == null) {
                      return null;
                    }
                    Box[] col = (Box[]) arg;
                    String[] col2 = new String[col.length];
                    for (int i = 0; i < col.length; i++) {
                      col2[i] = Casting.toString(col[i]);
                    }
                    return col2;
                  }
                },
                Pd.subEquals(2, __));
    Integer n1 = 0;
    Integer n2 = 0;
    Boolean found = false;
    String[] pd_2z = Perl.split(",", z);
    /* print STDERR "-$y-, -$z-, -@x-\n";*/
    for (Integer m = 0; Pd.checkNull(m) <= Pd.checkNull((x.length - 1)); m = Pd.checkNull(m) + 1) {
      String Dep = x[m];
      m = Pd.checkNull(m) + 1;
      String Head = x[m];
      String[] pd_20;
      pd_20 = Regex.matcher(Head, "[w]+_([0-9]+)", "", false);
      n1 = Casting.toInteger(pd_20[0]);
      String[] pd_21;
      pd_21 = Regex.matcher(Dep, "[w]+_([0-9]+)", "", false);
      n2 = Casting.toInteger(pd_21[0]);
      if (Pd.compare(Pd.checkNull(z), "") == 0) {
        Head = Lemma.get(n1) + "_" + Tag.get(n1) + "_" + n1;
        String Rel = y;
        Dep = Lemma.get(n2) + "_" + Tag.get(n2) + "_" + n2;
        Ordenar.put("(" + Rel + ";" + Head + ";" + Dep + ")", n2);
      } else {
        for (String atr : pd_2z) {
          if ((Pd.compare(Pd.checkNull(ATTR.get(n1).get(atr)), Pd.checkNull(ATTR.get(n2).get(atr)))
                  != 0)
              && (!Regex.match(ATTR.get(n1).get(atr), "^[NC0]$", "", false)
                  && !Regex.match(ATTR.get(n2).get(atr), "^[NC0]$", "", false))
              && (Pd.compare(Pd.checkNull(ATTR.get(n1).get(atr)), "") != 0
                  && Pd.compare(Pd.checkNull(ATTR.get(n2).get(atr)), "") != 0)) {
            found = true;
          }
        }
        /*  print STDERR "Found: $found\n";*/
        if (found) {
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + Tag.get(n1) + "_" + n1 + "_<)",
                      "$1concord:0\\|",
                      "",
                      false));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + Tag.get(n2) + "_" + n2 + "_<)",
                      "$1concord:0\\|",
                      "",
                      false));
          found = false;
        } else {
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + Tag.get(n1) + "_" + n1 + "_<)",
                      "$1concord:1\\|",
                      "",
                      false));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + Tag.get(n2) + "_" + n2 + "_<)",
                      "$1concord:1\\|",
                      "",
                      false));
          found = false;
          Head = Lemma.get(n1) + "_" + Tag.get(n1) + "_" + n1;
          String Rel = y;
          Dep = Lemma.get(n2) + "_" + Tag.get(n2) + "_" + n2;
          Ordenar.put("(" + Rel + ";" + Head + ";" + Dep + ")", n2);
        }
      }
    }
    return new Box[0];
  }

  public static Box[] DepRelHead(Box[] __) {
    String y;
    String z;
    String[] x;
    y = Casting.toString(__[0]);
    z = Casting.toString(__[1]);
    x =
        (String[])
            Casting.cast(
                new Casting() {
                  @Override
                  public Object casting(Object arg) {
                    if (arg == null) {
                      return null;
                    }
                    Box[] col = (Box[]) arg;
                    String[] col2 = new String[col.length];
                    for (int i = 0; i < col.length; i++) {
                      col2[i] = Casting.toString(col[i]);
                    }
                    return col2;
                  }
                },
                Pd.subEquals(2, __));
    Integer n1 = 0;
    Integer n2 = 0;
    Integer n3 = 0;
    Boolean found = false;
    String[] pd_3z = Perl.split(",", z);
    for (Integer m = 0; Pd.checkNull(m) <= Pd.checkNull((x.length - 1)); m = Pd.checkNull(m) + 1) {
      String Dep = x[m];
      m = Pd.checkNull(m) + 1;
      String Rel = x[m];
      m = Pd.checkNull(m) + 1;
      String Head = x[m];
      String[] pd_22;
      pd_22 = Regex.matcher(Head, "[w]+_([0-9]+)", "", false);
      n1 = Casting.toInteger(pd_22[0]);
      String[] pd_23;
      pd_23 = Regex.matcher(Rel, "[w]+_([0-9]+)", "", false);
      n2 = Casting.toInteger(pd_23[0]);
      String[] pd_24;
      pd_24 = Regex.matcher(Dep, "[w]+_([0-9]+)", "", false);
      n3 = Casting.toInteger(pd_24[0]);
      if (Pd.compare(Pd.checkNull(z), "") == 0) {
        Head = Lemma.get(n1) + "_" + Tag.get(n1) + "_" + n1;
        Rel = y + "/" + Lemma.get(n2) + "_" + Tag.get(n2) + "_" + n2;
        Dep = Lemma.get(n3) + "_" + Tag.get(n3) + "_" + n3;
        Ordenar.put("(" + Rel + ";" + Head + ";" + Dep + ")", n3);
      } else {
        for (String atr : pd_3z) {
          if ((Pd.compare(Pd.checkNull(ATTR.get(n1).get(atr)), Pd.checkNull(ATTR.get(n3).get(atr)))
                  != 0)
              && (!Regex.match(ATTR.get(n1).get(atr), "^[NC0]$", "", false)
                  && !Regex.match(ATTR.get(n3).get(atr), "^[NC0]$", "", false))
              && (Pd.compare(Pd.checkNull(ATTR.get(n1).get(atr)), "") != 0
                  && Pd.compare(Pd.checkNull(ATTR.get(n3).get(atr)), "") != 0)) {
            found = true;
          }
        }
        if (found) {
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + Tag.get(n1) + "_" + n1 + "_<)",
                      "$1concord:0\\|",
                      "",
                      false));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + Tag.get(n3) + "_" + n3 + "_<)",
                      "$1concord:0\\|",
                      "",
                      false));
          found = false;
        } else {
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + Tag.get(n1) + "_" + n1 + "_<)",
                      "$1concord:1\\|",
                      "",
                      false));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + Tag.get(n3) + "_" + n3 + "_<)",
                      "$1concord:1\\|",
                      "",
                      false));
          found = false;
          Head = Lemma.get(n1) + "_" + Tag.get(n1) + "_" + n1;
          Rel = y + "/" + Lemma.get(n2) + "_" + Tag.get(n2) + "_" + n2;
          Dep = Lemma.get(n3) + "_" + Tag.get(n3) + "_" + n3;
          Ordenar.put("(" + Rel + ";" + Head + ";" + Dep + ")", n3);
        }
      }
    }
    return new Box[0];
  }

  public static Box[] HeadRelDep(Box[] __) {
    String y;
    String z;
    String[] x;
    y = Casting.toString(__[0]);
    z = Casting.toString(__[1]);
    x =
        (String[])
            Casting.cast(
                new Casting() {
                  @Override
                  public Object casting(Object arg) {
                    if (arg == null) {
                      return null;
                    }
                    Box[] col = (Box[]) arg;
                    String[] col2 = new String[col.length];
                    for (int i = 0; i < col.length; i++) {
                      col2[i] = Casting.toString(col[i]);
                    }
                    return col2;
                  }
                },
                Pd.subEquals(2, __));
    Integer n1 = 0;
    Integer n2 = 0;
    Integer n3 = 0;
    Boolean found = false;
    String[] pd_4z = Perl.split(",", z);
    for (Integer m = 0; Pd.checkNull(m) <= Pd.checkNull((x.length - 1)); m = Pd.checkNull(m) + 1) {
      String Head = x[m];
      m = Pd.checkNull(m) + 1;
      String Rel = Pd.checkNull(y) + "_" + Pd.checkNull(x[m]);
      m = Pd.checkNull(m) + 1;
      String Dep = x[m];
      String[] pd_25;
      pd_25 = Regex.matcher(Head, "[w]+_([0-9]+)", "", false);
      n1 = Casting.toInteger(pd_25[0]);
      String[] pd_26;
      pd_26 = Regex.matcher(Rel, "[w]+_([0-9]+)", "", false);
      n2 = Casting.toInteger(pd_26[0]);
      String[] pd_27;
      pd_27 = Regex.matcher(Dep, "[w]+_([0-9]+)", "", false);
      n3 = Casting.toInteger(pd_27[0]);
      if (Pd.compare(Pd.checkNull(z), "") == 0) {
        Head = Lemma.get(n1) + "_" + Tag.get(n1) + "_" + n1;
        Rel = y + "/" + Lemma.get(n2) + "_" + Tag.get(n2) + "_" + n2;
        Dep = Lemma.get(n3) + "_" + Tag.get(n3) + "_" + n3;
        Ordenar.put("(" + Rel + ";" + Head + ";" + Dep + ")", n3);
      } else {
        for (String atr : pd_4z) {
          if ((Pd.compare(Pd.checkNull(ATTR.get(n1).get(atr)), Pd.checkNull(ATTR.get(n3).get(atr)))
                  != 0)
              && (!Regex.match(ATTR.get(n1).get(atr), "^[NC0]$", "", false)
                  && !Regex.match(ATTR.get(n3).get(atr), "^[NC0]$", "", false))
              && (Pd.compare(Pd.checkNull(ATTR.get(n1).get(atr)), "") != 0
                  && Pd.compare(Pd.checkNull(ATTR.get(n3).get(atr)), "") != 0)) {
            found = true;
          }
        }
        if (found) {
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + Tag.get(n1) + "_" + n1 + "_<)",
                      "$1concord:0\\|",
                      "",
                      false));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + Tag.get(n3) + "_" + n3 + "_<)",
                      "$1concord:0\\|",
                      "",
                      false));
          found = false;
        } else {
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + Tag.get(n1) + "_" + n1 + "_<)",
                      "$1concord:1\\|",
                      "",
                      false));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + Tag.get(n3) + "_" + n3 + "_<)",
                      "$1concord:1\\|",
                      "",
                      false));
          found = false;
          Head = Lemma.get(n1) + "_" + Tag.get(n1) + "_" + n1;
          Rel = y + "/" + Lemma.get(n2) + "_" + Tag.get(n2) + "_" + n2;
          Dep = Lemma.get(n3) + "_" + Tag.get(n3) + "_" + n3;
          Ordenar.put("(" + Rel + ";" + Head + ";" + Dep + ")", n3);
        }
      }
    }
    return new Box[0];
  }

  public static Box[] RelDepHead(Box[] __) {
    String y;
    String z;
    String[] x;
    y = Casting.toString(__[0]);
    z = Casting.toString(__[1]);
    x =
        (String[])
            Casting.cast(
                new Casting() {
                  @Override
                  public Object casting(Object arg) {
                    if (arg == null) {
                      return null;
                    }
                    Box[] col = (Box[]) arg;
                    String[] col2 = new String[col.length];
                    for (int i = 0; i < col.length; i++) {
                      col2[i] = Casting.toString(col[i]);
                    }
                    return col2;
                  }
                },
                Pd.subEquals(2, __));
    Integer n1 = 0;
    Integer n2 = 0;
    Integer n3 = 0;
    Boolean found = false;
    String[] pd_5z = Perl.split(",", z);
    for (Integer m = 0; Pd.checkNull(m) <= Pd.checkNull((x.length - 1)); m = Pd.checkNull(m) + 1) {
      String Rel = x[m];
      m = Pd.checkNull(m) + 1;
      String Dep = x[m];
      m = Pd.checkNull(m) + 1;
      String Head = x[m];
      String[] pd_28;
      pd_28 = Regex.matcher(Head, "[w]+_([0-9]+)", "", false);
      n1 = Casting.toInteger(pd_28[0]);
      String[] pd_29;
      pd_29 = Regex.matcher(Rel, "[w]+_([0-9]+)", "", false);
      n2 = Casting.toInteger(pd_29[0]);
      String[] pd_30;
      pd_30 = Regex.matcher(Dep, "[w]+_([0-9]+)", "", false);
      n3 = Casting.toInteger(pd_30[0]);
      if (Pd.compare(Pd.checkNull(z), "") == 0) {
        Head = Lemma.get(n1) + "_" + Tag.get(n1) + "_" + n1;
        Rel = y + "/" + Lemma.get(n2) + "_" + Tag.get(n2) + "_" + n2;
        Dep = Lemma.get(n3) + "_" + Tag.get(n3) + "_" + n3;
        Ordenar.put("(" + Rel + ";" + Head + ";" + Dep + ")", n3);
      } else {
        for (String atr : pd_5z) {
          if ((Pd.compare(Pd.checkNull(ATTR.get(n1).get(atr)), Pd.checkNull(ATTR.get(n3).get(atr)))
                  != 0)
              && (!Regex.match(ATTR.get(n1).get(atr), "^[NC0]$", "", false)
                  && !Regex.match(ATTR.get(n3).get(atr), "^[NC0]$", "", false))
              && (Pd.compare(Pd.checkNull(ATTR.get(n1).get(atr)), "") != 0
                  && Pd.compare(Pd.checkNull(ATTR.get(n3).get(atr)), "") != 0)) {
            found = true;
          }
        }
        if (found) {
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + Tag.get(n1) + "_" + n1 + "_<)",
                      "$1concord:0\\|",
                      "",
                      false));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + Tag.get(n3) + "_" + n3 + "_<)",
                      "$1concord:0\\|",
                      "",
                      false));
          found = false;
        } else {
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + Tag.get(n1) + "_" + n1 + "_<)",
                      "$1concord:1\\|",
                      "",
                      false));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + Tag.get(n3) + "_" + n3 + "_<)",
                      "$1concord:1\\|",
                      "",
                      false));
          found = false;
          Head = Lemma.get(n1) + "_" + Tag.get(n1) + "_" + n1;
          Rel = y + "/" + Lemma.get(n2) + "_" + Tag.get(n2) + "_" + n2;
          Dep = Lemma.get(n3) + "_" + Tag.get(n3) + "_" + n3;
          Ordenar.put("(" + Rel + ";" + Head + ";" + Dep + ")", n3);
        }
      }
    }
    return new Box[0];
  }

  public static Box[] RelHeadDep(Box[] __) {
    String y;
    String z;
    String[] x;
    y = Casting.toString(__[0]);
    z = Casting.toString(__[1]);
    x =
        (String[])
            Casting.cast(
                new Casting() {
                  @Override
                  public Object casting(Object arg) {
                    if (arg == null) {
                      return null;
                    }
                    Box[] col = (Box[]) arg;
                    String[] col2 = new String[col.length];
                    for (int i = 0; i < col.length; i++) {
                      col2[i] = Casting.toString(col[i]);
                    }
                    return col2;
                  }
                },
                Pd.subEquals(2, __));
    Integer n1 = 0;
    Integer n2 = 0;
    Integer n3 = 0;
    Boolean found = false;
    String[] pd_6z = Perl.split(",", z);
    for (Integer m = 0; Pd.checkNull(m) <= Pd.checkNull((x.length - 1)); m = Pd.checkNull(m) + 1) {
      String Rel = x[m];
      m = Pd.checkNull(m) + 1;
      String Head = x[m];
      m = Pd.checkNull(m) + 1;
      String Dep = x[m];
      String[] pd_31;
      pd_31 = Regex.matcher(Head, "[w]+_([0-9]+)", "", false);
      n1 = Casting.toInteger(pd_31[0]);
      String[] pd_32;
      pd_32 = Regex.matcher(Rel, "[w]+_([0-9]+)", "", false);
      n2 = Casting.toInteger(pd_32[0]);
      String[] pd_33;
      pd_33 = Regex.matcher(Dep, "[w]+_([0-9]+)", "", false);
      n3 = Casting.toInteger(pd_33[0]);
      if (Pd.compare(Pd.checkNull(z), "") == 0) {
        Head = Lemma.get(n1) + "_" + Tag.get(n1) + "_" + n1;
        Rel = y + "/" + Lemma.get(n2) + "_" + Tag.get(n2) + "_" + n2;
        Dep = Lemma.get(n3) + "_" + Tag.get(n3) + "_" + n3;
        Ordenar.put("(" + Rel + ";" + Head + ";" + Dep + ")", n3);
      } else {
        for (String atr : pd_6z) {
          if ((Pd.compare(Pd.checkNull(ATTR.get(n1).get(atr)), Pd.checkNull(ATTR.get(n3).get(atr)))
                  != 0)
              && (!Regex.match(ATTR.get(n1).get(atr), "^[NC0]$", "", false)
                  && !Regex.match(ATTR.get(n3).get(atr), "^[NC0]$", "", false))
              && (Pd.compare(Pd.checkNull(ATTR.get(n1).get(atr)), "") != 0
                  && Pd.compare(Pd.checkNull(ATTR.get(n3).get(atr)), "") != 0)) {
            found = true;
          }
        }
        if (found) {
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + Tag.get(n1) + "_" + n1 + "_<)",
                      "$1concord:0\\|",
                      "",
                      false));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + Tag.get(n3) + "_" + n3 + "_<)",
                      "$1concord:0\\|",
                      "",
                      false));
          found = false;
        } else {
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + Tag.get(n1) + "_" + n1 + "_<)",
                      "$1concord:1\\|",
                      "",
                      false));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + Tag.get(n3) + "_" + n3 + "_<)",
                      "$1concord:1\\|",
                      "",
                      false));
          found = false;
          Head = Lemma.get(n1) + "_" + Tag.get(n1) + "_" + n1;
          Rel = y + "/" + Lemma.get(n2) + "_" + Tag.get(n2) + "_" + n2;
          Dep = Lemma.get(n3) + "_" + Tag.get(n3) + "_" + n3;
          Ordenar.put("(" + Rel + ";" + Head + ";" + Dep + ")", n3);
        }
      }
    }
    return new Box[0];
  }

  public static Box[] DepHeadRel(Box[] __) {
    String y;
    String z;
    String[] x;
    y = Casting.toString(__[0]);
    z = Casting.toString(__[1]);
    x =
        (String[])
            Casting.cast(
                new Casting() {
                  @Override
                  public Object casting(Object arg) {
                    if (arg == null) {
                      return null;
                    }
                    Box[] col = (Box[]) arg;
                    String[] col2 = new String[col.length];
                    for (int i = 0; i < col.length; i++) {
                      col2[i] = Casting.toString(col[i]);
                    }
                    return col2;
                  }
                },
                Pd.subEquals(2, __));
    Integer n1 = 0;
    Integer n2 = 0;
    Integer n3 = 0;
    Boolean found = false;
    String[] pd_7z = Perl.split(",", z);
    for (Integer m = 0; Pd.checkNull(m) <= Pd.checkNull((x.length - 1)); m = Pd.checkNull(m) + 1) {
      String Dep = x[m];
      m = Pd.checkNull(m) + 1;
      String Head = x[m];
      m = Pd.checkNull(m) + 1;
      String Rel = x[m];
      String[] pd_34;
      pd_34 = Regex.matcher(Head, "[w]+_([0-9]+)", "", false);
      n1 = Casting.toInteger(pd_34[0]);
      String[] pd_35;
      pd_35 = Regex.matcher(Rel, "[w]+_([0-9]+)", "", false);
      n2 = Casting.toInteger(pd_35[0]);
      String[] pd_36;
      pd_36 = Regex.matcher(Dep, "[w]+_([0-9]+)", "", false);
      n3 = Casting.toInteger(pd_36[0]);
      if (Pd.compare(Pd.checkNull(z), "") == 0) {
        Head = Lemma.get(n1) + "_" + Tag.get(n1) + "_" + n1;
        Rel = y + "/" + Lemma.get(n2) + "_" + Tag.get(n2) + "_" + n2;
        Dep = Lemma.get(n3) + "_" + Tag.get(n3) + "_" + n3;
        Ordenar.put("(" + Rel + ";" + Head + ";" + Dep + ")", n3);
      } else {
        for (String atr : pd_7z) {
          if ((Pd.compare(Pd.checkNull(ATTR.get(n1).get(atr)), Pd.checkNull(ATTR.get(n3).get(atr)))
                  != 0)
              && (!Regex.match(ATTR.get(n1).get(atr), "^[NC0]$", "", false)
                  && !Regex.match(ATTR.get(n3).get(atr), "^[NC0]$", "", false))
              && (Pd.compare(Pd.checkNull(ATTR.get(n1).get(atr)), "") != 0
                  && Pd.compare(Pd.checkNull(ATTR.get(n3).get(atr)), "") != 0)) {
            found = true;
          }
        }
        if (found) {
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + Tag.get(n1) + "_" + n1 + "_<)",
                      "$1concord:0\\|",
                      "",
                      false));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + Tag.get(n3) + "_" + n3 + "_<)",
                      "$1concord:0\\|",
                      "",
                      false));
          found = false;
        } else {
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + Tag.get(n1) + "_" + n1 + "_<)",
                      "$1concord:1\\|",
                      "",
                      false));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + Tag.get(n3) + "_" + n3 + "_<)",
                      "$1concord:1\\|",
                      "",
                      false));
          found = false;
          Head = Lemma.get(n1) + "_" + Tag.get(n1) + "_" + n1;
          Rel = y + "/" + Lemma.get(n2) + "_" + Tag.get(n2) + "_" + n2;
          Dep = Lemma.get(n3) + "_" + Tag.get(n3) + "_" + n3;
          Ordenar.put("(" + Rel + ";" + Head + ";" + Dep + ")", n3);
        }
      }
    }
    return new Box[0];
  }

  public static Box[] HeadDepRel(Box[] __) {
    String y;
    String z;
    String[] x;
    y = Casting.toString(__[0]);
    z = Casting.toString(__[1]);
    x =
        (String[])
            Casting.cast(
                new Casting() {
                  @Override
                  public Object casting(Object arg) {
                    if (arg == null) {
                      return null;
                    }
                    Box[] col = (Box[]) arg;
                    String[] col2 = new String[col.length];
                    for (int i = 0; i < col.length; i++) {
                      col2[i] = Casting.toString(col[i]);
                    }
                    return col2;
                  }
                },
                Pd.subEquals(2, __));
    Integer n1 = 0;
    Integer n2 = 0;
    Integer n3 = 0;
    Boolean found = false;
    String[] pd_8z = Perl.split(",", z);
    for (Integer m = 0; Pd.checkNull(m) <= Pd.checkNull((x.length - 1)); m = Pd.checkNull(m) + 1) {
      String Head = x[m];
      m = Pd.checkNull(m) + 1;
      String Rel = x[m];
      m = Pd.checkNull(m) + 1;
      String Dep = x[m];
      String[] pd_37;
      pd_37 = Regex.matcher(Head, "[w]+_([0-9]+)", "", false);
      n1 = Casting.toInteger(pd_37[0]);
      String[] pd_38;
      pd_38 = Regex.matcher(Rel, "[w]+_([0-9]+)", "", false);
      n2 = Casting.toInteger(pd_38[0]);
      String[] pd_39;
      pd_39 = Regex.matcher(Dep, "[w]+_([0-9]+)", "", false);
      n3 = Casting.toInteger(pd_39[0]);
      if (Pd.compare(Pd.checkNull(z), "") == 0) {
        Head = Lemma.get(n1) + "_" + Tag.get(n1) + "_" + n1;
        Rel = y + "/" + Lemma.get(n2) + "_" + Tag.get(n2) + "_" + n2;
        Dep = Lemma.get(n3) + "_" + Tag.get(n3) + "_" + n3;
        Ordenar.put("(" + Rel + ";" + Head + ";" + Dep + ")", n3);
      } else {
        for (String atr : pd_8z) {
          if ((Pd.compare(Pd.checkNull(ATTR.get(n1).get(atr)), Pd.checkNull(ATTR.get(n3).get(atr)))
                  != 0)
              && (!Regex.match(ATTR.get(n1).get(atr), "^[NC0]$", "", false)
                  && !Regex.match(ATTR.get(n3).get(atr), "^[NC0]$", "", false))
              && (Pd.compare(Pd.checkNull(ATTR.get(n1).get(atr)), "") != 0
                  && Pd.compare(Pd.checkNull(ATTR.get(n3).get(atr)), "") != 0)) {
            found = true;
          }
        }
        if (found) {
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + Tag.get(n1) + "_" + n1 + "_<)",
                      "$1concord:0\\|",
                      "",
                      false));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + Tag.get(n3) + "_" + n3 + "_<)",
                      "$1concord:0\\|",
                      "",
                      false));
          found = false;
        } else {
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + Tag.get(n1) + "_" + n1 + "_<)",
                      "$1concord:1\\|",
                      "",
                      false));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + Tag.get(n3) + "_" + n3 + "_<)",
                      "$1concord:1\\|",
                      "",
                      false));
          found = false;
          Head = Lemma.get(n1) + "_" + Tag.get(n1) + "_" + n1;
          Rel = y + "/" + Lemma.get(n2) + "_" + Tag.get(n2) + "_" + n2;
          Dep = Lemma.get(n3) + "_" + Tag.get(n3) + "_" + n3;
          Ordenar.put("(" + Rel + ";" + Head + ";" + Dep + ")", n3);
        }
      }
    }
    return new Box[0];
  }

  public static Box[] HeadDep_lex(Box[] __) {
    String y;
    String z;
    String[] x;
    y = Casting.toString(__[0]);
    z = Casting.toString(__[1]);
    x =
        (String[])
            Casting.cast(
                new Casting() {
                  @Override
                  public Object casting(Object arg) {
                    if (arg == null) {
                      return null;
                    }
                    Box[] col = (Box[]) arg;
                    String[] col2 = new String[col.length];
                    for (int i = 0; i < col.length; i++) {
                      col2[i] = Casting.toString(col[i]);
                    }
                    return col2;
                  }
                },
                Pd.subEquals(2, __));
    Integer n1 = 0;
    Integer n2 = 0;
    Boolean found = false;
    String[] pd_9z = Perl.split(",", z);
    for (Integer m = 0; Pd.checkNull(m) <= Pd.checkNull((x.length - 1)); m = Pd.checkNull(m) + 1) {
      String Head = x[m];
      m = Pd.checkNull(m) + 1;
      String Dep = x[m];
      String[] pd_40;
      pd_40 = Regex.matcher(Head, "[w]+_([0-9]+)", "", false);
      n1 = Casting.toInteger(pd_40[0]);
      String[] pd_41;
      pd_41 = Regex.matcher(Dep, "[w]+_([0-9]+)", "", false);
      n2 = Casting.toInteger(pd_41[0]);
      if (Pd.compare(Pd.checkNull(z), "") == 0) {
        /*$Head = "$Lemma[$n1]_$Tag[$n1]_${n1}" ;*/
        Head = ATTR.get(n1).get("lemma") + "_" + Tag.get(n1) + "_" + n1;
        String Rel = y;
        /*$Dep = "$Lemma[$n2]_$Tag[$n2]_${n2}" ;*/
        Dep = ATTR.get(n2).get("lemma") + "_" + Tag.get(n2) + "_" + n2;
        Ordenar.put("(" + Rel + ";" + Head + ";" + Dep + ")", n2);
        /*print STDERR "head::$Head -- dep:::$Dep\n";*/
        if (!Perl.defined(
            Casting.toBoolean(ATTR_lemma.get(Casting.toString(n1)))
                && !Perl.defined(ATTR_lemma.get(Casting.toString(n2))))) {
          ATTR_lemma.put(
              Casting.toString(n1), Pd.checkNull(ATTR.get(n1).get("lemma")) + "@" + Lemma.get(n2));
          /* $ATTR_token{$n1} = $ATTR[$n1]{"token"} .  "\@$Token[$n2]";*/
          IDF.put(
              Casting.toString(n1),
              Casting.toString(Pd.checkNull(Casting.toDouble(IDF.get(Casting.toString(n1)))) + 1));
        } else if (!Perl.defined(
            Casting.toBoolean(ATTR_lemma.get(Casting.toString(n1)))
                && Perl.defined(ATTR_lemma.get(Casting.toString(n2))))) {
          ATTR_lemma.put(
              Casting.toString(n1),
              Pd.checkNull(ATTR.get(n1).get("lemma")) + "@" + ATTR_lemma.get(Casting.toString(n2)));
          /*$ATTR_token{$n1} = $ATTR[$n1]{"token"} .   "\@$ATTR_token{$n2}";*/
          IDF.put(
              Casting.toString(n1),
              Casting.toString(Pd.checkNull(Casting.toDouble(IDF.get(Casting.toString(n1)))) + 1));
        } else if (Perl.defined(
            Casting.toBoolean(ATTR_lemma.get(Casting.toString(n1)))
                && !Perl.defined(ATTR_lemma.get(Casting.toString(n2))))) {
          ATTR_lemma.put(
              Casting.toString(n1),
              Pd.checkNull(ATTR_lemma.get(Casting.toString(n1))) + "@" + Lemma.get(n2));
          /*$ATTR_token{$n1} .=   "\@$Token[$n2]";*/
        } else {
          ATTR_lemma.put(
              Casting.toString(n1),
              Pd.checkNull(ATTR_lemma.get(Casting.toString(n1)))
                  + "@"
                  + ATTR_lemma.get(Casting.toString(n2)));
          /*  $ATTR_token{$n1} .=    "\@$ATTR_token{$n2}" ;*/
        }
        /*print STDERR "$n1::: $ATTR_lemma{$n1} -- $ATTR_token{$n1} \n";*/
      } else {
        for (String atr : pd_9z) {
          if ((Pd.compare(Pd.checkNull(ATTR.get(n1).get(atr)), Pd.checkNull(ATTR.get(n2).get(atr)))
                  != 0)
              && (!Regex.match(ATTR.get(n1).get(atr), "^[NC0]$", "", false)
                  && !Regex.match(ATTR.get(n2).get(atr), "^[NC0]$", "", false))
              && (Pd.compare(Pd.checkNull(ATTR.get(n1).get(atr)), "") != 0
                  && Pd.compare(Pd.checkNull(ATTR.get(n2).get(atr)), "") != 0)) {
            found = true;
          }
        }
        /* print STDERR "Found: $found\n";*/
        if (found) {
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + Tag.get(n1) + "_" + n1 + "_<)",
                      "$1concord:0\\|",
                      "",
                      false));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + Tag.get(n2) + "_" + n2 + "_<)",
                      "$1concord:0\\|",
                      "",
                      false));
          found = false;
        } else {
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + Tag.get(n1) + "_" + n1 + "_<)",
                      "$1concord:1\\|",
                      "",
                      false));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + Tag.get(n2) + "_" + n2 + "_<)",
                      "$1concord:1\\|",
                      "",
                      false));
          found = false;
          /*$Head = "$Lemma[$n1]_$Tag[$n1]_${n1}" ;*/
          Head = ATTR.get(n1).get("lemma") + "_" + Tag.get(n1) + "_" + n1;
          String Rel = y;
          /*$Dep = "$Lemma[$n2]_$Tag[$n2]_${n2}" ;*/
          Dep = ATTR.get(n2).get("lemma") + "_" + Tag.get(n2) + "_" + n2;
          Ordenar.put("(" + Rel + ";" + Head + ";" + Dep + ")", n2);
          if (!Perl.defined(
              Casting.toBoolean(ATTR_lemma.get(Casting.toString(n1)))
                  && !Perl.defined(ATTR_lemma.get(Casting.toString(n2))))) {
            ATTR_lemma.put(
                Casting.toString(n1),
                Pd.checkNull(ATTR.get(n1).get("lemma")) + "@" + Lemma.get(n2));
            /* $ATTR_token{$n1} = $ATTR[$n1]{"token"} .  "\@$Token[$n2]";*/
            IDF.put(
                Casting.toString(n1),
                Casting.toString(
                    Pd.checkNull(Casting.toDouble(IDF.get(Casting.toString(n1)))) + 1));
          } else if (!Perl.defined(
              Casting.toBoolean(ATTR_lemma.get(Casting.toString(n1)))
                  && Perl.defined(ATTR_lemma.get(Casting.toString(n2))))) {
            ATTR_lemma.put(
                Casting.toString(n1),
                Pd.checkNull(ATTR.get(n1).get("lemma"))
                    + "@"
                    + ATTR_lemma.get(Casting.toString(n2)));
            /* $ATTR_token{$n1} = $ATTR[$n1]{"token"} .   "\@$ATTR_token{$n2}";*/
            IDF.put(
                Casting.toString(n1),
                Casting.toString(
                    Pd.checkNull(Casting.toDouble(IDF.get(Casting.toString(n1)))) + 1));
          } else if (Perl.defined(
              Casting.toBoolean(ATTR_lemma.get(Casting.toString(n1)))
                  && !Perl.defined(ATTR_lemma.get(Casting.toString(n2))))) {
            ATTR_lemma.put(
                Casting.toString(n1),
                Pd.checkNull(ATTR_lemma.get(Casting.toString(n1))) + "@" + Lemma.get(n2));
            /* $ATTR_token{$n1} .=   "\@$Token[$n2]";*/
          } else {
            ATTR_lemma.put(
                Casting.toString(n1),
                Pd.checkNull(ATTR_lemma.get(Casting.toString(n1)))
                    + "@"
                    + ATTR_lemma.get(Casting.toString(n2)));
            /*   $ATTR_token{$n1} .=    "\@$ATTR_token{$n2}" ;*/
          }
        }
      }
      Lemma.set(n1, ATTR_lemma.get(Casting.toString(n1)));
      /* $Token[$n1] = $ATTR_token{$n1} ;*/
    }
    return new Box[0];
  }

  public static Box[] DepHead_lex(Box[] __) {
    String y;
    String z;
    String[] x;
    y = Casting.toString(__[0]);
    z = Casting.toString(__[1]);
    x =
        (String[])
            Casting.cast(
                new Casting() {
                  @Override
                  public Object casting(Object arg) {
                    if (arg == null) {
                      return null;
                    }
                    Box[] col = (Box[]) arg;
                    String[] col2 = new String[col.length];
                    for (int i = 0; i < col.length; i++) {
                      col2[i] = Casting.toString(col[i]);
                    }
                    return col2;
                  }
                },
                Pd.subEquals(2, __));
    Integer n1 = 0;
    Integer n2 = 0;
    Boolean found = false;
    String[] pd_10z = Perl.split(",", z);
    for (Integer m = 0; Pd.checkNull(m) <= Pd.checkNull((x.length - 1)); m = Pd.checkNull(m) + 1) {
      String Dep = x[m];
      m = Pd.checkNull(m) + 1;
      String Head = x[m];
      String[] pd_42;
      pd_42 = Regex.matcher(Head, "[w]+_([0-9]+)", "", false);
      n1 = Casting.toInteger(pd_42[0]);
      String[] pd_43;
      pd_43 = Regex.matcher(Dep, "[w]+_([0-9]+)", "", false);
      n2 = Casting.toInteger(pd_43[0]);
      if (Pd.compare(Pd.checkNull(z), "") == 0) {
        /*$Head = "$Lemma[$n1]_$Tag[$n1]_${n1}" ;*/
        Head = ATTR.get(n1).get("lemma") + "_" + Tag.get(n1) + "_" + n1;
        String Rel = y;
        /*$Dep = "$Lemma[$n2]_$Tag[$n2]_${n2}" ;*/
        Dep = ATTR.get(n2).get("lemma") + "_" + Tag.get(n2) + "_" + n2;
        Ordenar.put("(" + Rel + ";" + Head + ";" + Dep + ")", n2);
        /*print STDERR "OKKKK: #$Dep - $n2#\n";*/
        if (!Perl.defined(
            Casting.toBoolean(ATTR_lemma.get(Casting.toString(n1)))
                && !Perl.defined(ATTR_lemma.get(Casting.toString(n2))))) {
          ATTR_lemma.put(
              Casting.toString(n1), Pd.checkNull(ATTR.get(n2).get("lemma")) + "@" + Lemma.get(n1));
          /*       $ATTR_token{$n1} = $ATTR[$n2]{"token"} .  "\@$Token[$n1]";*/
          IDF.put(
              Casting.toString(n1),
              Casting.toString(Pd.checkNull(Casting.toDouble(IDF.get(Casting.toString(n1)))) + 1));
        } else if (!Perl.defined(
            Casting.toBoolean(ATTR_lemma.get(Casting.toString(n1)))
                && Perl.defined(ATTR_lemma.get(Casting.toString(n2))))) {
          ATTR_lemma.put(
              Casting.toString(n1),
              ATTR_lemma.get(Casting.toString(n2)) + "@" + Pd.checkNull(ATTR.get(n1).get("lemma")));
          /*      $ATTR_token{$n1} =   "$ATTR_token{$n2}\@" .  $ATTR[$n1]{"token"};*/
          IDF.put(
              Casting.toString(n1),
              Casting.toString(Pd.checkNull(Casting.toDouble(IDF.get(Casting.toString(n1)))) + 1));
        } else if (Perl.defined(
            Casting.toBoolean(ATTR_lemma.get(Casting.toString(n1)))
                && !Perl.defined(ATTR_lemma.get(Casting.toString(n2))))) {
          ATTR_lemma.put(
              Casting.toString(n1),
              Pd.checkNull(ATTR_lemma.get(Casting.toString(n1))) + Lemma.get(n2) + "@");
          /*     $ATTR_token{$n1} .=   "$Token[$n2]\@" ;*/
        } else {
          ATTR_lemma.put(
              Casting.toString(n1),
              Pd.checkNull(ATTR_lemma.get(Casting.toString(n1)))
                  + ATTR_lemma.get(Casting.toString(n1))
                  + "@");
          /*       $ATTR_token{$n1} .=    "$ATTR_token{$n1}\@" ;*/
        }
      } else {
        for (String atr : pd_10z) {
          if ((Pd.compare(Pd.checkNull(ATTR.get(n1).get(atr)), Pd.checkNull(ATTR.get(n2).get(atr)))
                  != 0)
              && (!Regex.match(ATTR.get(n1).get(atr), "^[NC0]$", "", false)
                  && !Regex.match(ATTR.get(n2).get(atr), "^[NC0]$", "", false))
              && (Pd.compare(Pd.checkNull(ATTR.get(n1).get(atr)), "") != 0
                  && Pd.compare(Pd.checkNull(ATTR.get(n2).get(atr)), "") != 0)) {
            found = true;
          }
        }
        /* print STDERR "Found: $found\n";*/
        if (found) {
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + Tag.get(n1) + "_" + n1 + "_<)",
                      "$1concord:0\\|",
                      "",
                      false));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + Tag.get(n2) + "_" + n2 + "_<)",
                      "$1concord:0\\|",
                      "",
                      false));
          found = false;
        } else {
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + Tag.get(n1) + "_" + n1 + "_<)",
                      "$1concord:1\\|",
                      "",
                      false));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + Tag.get(n2) + "_" + n2 + "_<)",
                      "$1concord:1\\|",
                      "",
                      false));
          found = false;
          /*$Head = "$Lemma[$n1]_$Tag[$n1]_${n1}" ;*/
          Head = ATTR.get(n1).get("lemma") + "_" + Tag.get(n1) + "_" + n1;
          String Rel = y;
          /*$Dep = "$Lemma[$n2]_$Tag[$n2]_${n2}" ;*/
          Dep = ATTR.get(n2).get("lemma") + "_" + Tag.get(n2) + "_" + n2;
          Ordenar.put("(" + Rel + ";" + Head + ";" + Dep + ")", n2);
          if (!Perl.defined(
              Casting.toBoolean(ATTR_lemma.get(Casting.toString(n1)))
                  && !Perl.defined(ATTR_lemma.get(Casting.toString(n2))))) {
            ATTR_lemma.put(
                Casting.toString(n1),
                Pd.checkNull(ATTR.get(n2).get("lemma")) + "@" + Lemma.get(n1));
            /*   $ATTR_token{$n1} = $ATTR[$n2]{"token"} .  "\@$Token[$n1]";*/
            IDF.put(
                Casting.toString(n1),
                Casting.toString(
                    Pd.checkNull(Casting.toDouble(IDF.get(Casting.toString(n1)))) + 1));
          } else if (!Perl.defined(
              Casting.toBoolean(ATTR_lemma.get(Casting.toString(n1)))
                  && Perl.defined(ATTR_lemma.get(Casting.toString(n2))))) {
            ATTR_lemma.put(
                Casting.toString(n1),
                ATTR_lemma.get(Casting.toString(n2))
                    + "@"
                    + Pd.checkNull(ATTR.get(n1).get("lemma")));
            /*  $ATTR_token{$n1} =   "$ATTR_token{$n2}\@" .  $ATTR[$n1]{"token"};*/
            IDF.put(
                Casting.toString(n1),
                Casting.toString(
                    Pd.checkNull(Casting.toDouble(IDF.get(Casting.toString(n1)))) + 1));
          } else if (Perl.defined(
              Casting.toBoolean(ATTR_lemma.get(Casting.toString(n1)))
                  && !Perl.defined(ATTR_lemma.get(Casting.toString(n2))))) {
            ATTR_lemma.put(
                Casting.toString(n1),
                Pd.checkNull(ATTR_lemma.get(Casting.toString(n1))) + Lemma.get(n2) + "@");
            /* $ATTR_token{$n1} .=   "$Token[$n2]\@" ;*/
          } else {
            ATTR_lemma.put(
                Casting.toString(n1),
                Pd.checkNull(ATTR_lemma.get(Casting.toString(n1)))
                    + ATTR_lemma.get(Casting.toString(n1))
                    + "@");
            /*   $ATTR_token{$n1} .=    "$ATTR_token{$n1}\@" ;*/
          }
        }
      }
      Lemma.set(n1, ATTR_lemma.get(Casting.toString(n1)));
      /* $Token[$n1] = $ATTR_token{$n1} ;*/
    }
    return new Box[0];
  }

  public static Box[] Head(Box[] __) {
    String y;
    String z;
    String[] x;
    y = Casting.toString(__[0]);
    z = Casting.toString(__[1]);
    x =
        (String[])
            Casting.cast(
                new Casting() {
                  @Override
                  public Object casting(Object arg) {
                    if (arg == null) {
                      return null;
                    }
                    Box[] col = (Box[]) arg;
                    String[] col2 = new String[col.length];
                    for (int i = 0; i < col.length; i++) {
                      col2[i] = Casting.toString(col[i]);
                    }
                    return col2;
                  }
                },
                Pd.subEquals(2, __));
    return new Box[] {Casting.box(1)};
  }

  public static Box[] LEX(Box[] __) {
    for (String idf : Perl.keys(IDF)) {
      /*print STDERR "idf = $idf";
      #parche para \2... \pi...:
       $ATTR[$idf]{"lemma"}  =~ s/[\\]/\\\\/g ;
       $ATTR[$idf]{"token"}  =~ s/[\\]/\\\\/g ;*/
      listTags =
          Casting.toInteger(
              Regex.substitution(
                  Casting.toString(listTags),
                  "("
                      + Tag.get(Casting.toInteger(idf))
                      + "_"
                      + idf
                      + l
                      + ")lemma:"
                      + ATTR.get(Casting.toInteger(idf)).get("lemma"),
                  "${1}lemma:" + ATTR_lemma.get(idf),
                  "",
                  false));
      /* $listTags =~ s/($Tag[$idf]_${idf}${l})token:$ATTR[$idf]{"token"}/${1}token:$ATTR_token{$idf}/;*/
      Perl.delete(IDF, idf);
      Perl.delete(ATTR_lemma, idf);
      /*delete $ATTR_token{$idf};*/
    }
    return new Box[0];
  }
  /*#Operaçoes Corr, Inherit, Add, */
  public static Box[] Corr(Box[] __) {
    String y;
    String z;
    String[] x;
    y = Casting.toString(__[0]);
    z = Casting.toString(__[1]);
    x =
        (String[])
            Casting.cast(
                new Casting() {
                  @Override
                  public Object casting(Object arg) {
                    if (arg == null) {
                      return null;
                    }
                    Box[] col = (Box[]) arg;
                    String[] col2 = new String[col.length];
                    for (int i = 0; i < col.length; i++) {
                      col2[i] = Casting.toString(col[i]);
                    }
                    return col2;
                  }
                },
                Pd.subEquals(2, __));
    Integer n1 = 0;
    Integer n2 = 0;
    String atr = "";
    String value = "";
    String pd_2info = "";
    String attribute = "";
    String entry = "";
    String[] pd_1y = Perl.split(",", y);
    if (Pd.compare(Pd.checkNull(z), "Head") == 0) {
      for (Integer m = 0;
          Pd.checkNull(m) <= Pd.checkNull((x.length - 1));
          m = Pd.checkNull(m) + 1) {
        String Head = x[m];
        String[] pd_44;
        pd_44 = Regex.matcher(Head, "[w]+_([0-9]+)", "", false);
        n1 = Casting.toInteger(pd_44[0]);
        for (String pd_3info : pd_1y) {
          String[] pd_45;
          pd_45 = Perl.split(":", pd_3info);
          atr = pd_45[0];
          value = pd_45[1];
          /*#token:=lemma / lemma:=token*/
          if (Regex.match(value, "^=", "", false)) {
            value = Regex.substitution(value, "^=", "", "", false);
            ATTR.get(n1).put(atr, ATTR.get(n1).get(value));
            if (Pd.compare(Pd.checkNull(value), "token") == 0) {
              Lemma.set(n1, ATTR.get(n1).get(value));
            } else if (Pd.compare(Pd.checkNull(value), "lemma") == 0) {
              Token.set(n1, ATTR.get(n1).get(value));
            }
          }
          /*#change the PoS tag:*/
          else if (Regex.match(atr, "^tag", "", false)) {
            ATTR.get(n1).put(atr, value);
            entry = value + "_" + n1 + "_<";
            if ((activarTags(new Box[] {Casting.box(value), Casting.box(n1)}).length > 0)) {
              for (String pd_1attribute : Perl.sort(Perl.keys(ATTR.get(n1)))) {
                /* print STDERR "--atribs: $attribute\n";      */
                if (Perl.defined(TagStr.get(pd_1attribute))) {
                  entry =
                      Pd.checkNull(entry)
                          + pd_1attribute
                          + ":"
                          + ATTR.get(n1).get(pd_1attribute)
                          + "|";
                  /*print STDERR "atribute defined : $attribute --$entry\n";*/
                } else {
                  /*$entry .= "$attribute:$TagStr{$attribute}|" ;*/
                  Perl.delete(ATTR.get(n1), pd_1attribute);
                  /*print STDERR "++entry : $entry\n";*/
                }
              }
              for (String pd_2attribute : Perl.sort(Perl.keys(TagStr))) {
                /* print STDERR "++atribs: $attribute\n";      */
                if (!Perl.defined(ATTR.get(n1).get(pd_2attribute))) {
                  entry =
                      Pd.checkNull(entry)
                          + pd_2attribute
                          + ":"
                          + ATTR.get(n1).get(pd_2attribute)
                          + "|";
                  ATTR.get(n1).put(pd_2attribute, Casting.toString(TagStr.get(pd_2attribute)));
                  /*print STDERR "++atribute defined : $attribute --$entry\n";*/
                }
              }
            }
            entry = Pd.checkNull(entry) + ">";
            /*print STDERR  "--$entry\n" ;*/
            listTags =
                Casting.toInteger(
                    Regex.substitution(
                        Casting.toString(listTags), Tag.get(n1) + "_" + n1 + a2, entry, "", false));
            Tag.set(n1, value);
            desactivarTags(new Box[] {Casting.box(value)});
          } else if (Regex.match(
              Casting.toString(listTags), Tag.get(n1) + "_" + n1 + l + atr, "", false)) {
            ATTR.get(n1).put(atr, value);
            if (Pd.compare(Pd.checkNull(atr), "lemma") == 0) {
              Lemma.set(n1, ATTR.get(n1).get("lemma"));
            }
            if (Pd.compare(Pd.checkNull(atr), "token") == 0) {
              Token.set(n1, ATTR.get(n1).get("token"));
            }
            listTags =
                Casting.toInteger(
                    Regex.substitution(
                        Casting.toString(listTags),
                        "(" + Tag.get(n1) + "_" + n1 + l + ")" + atr + ":[^|]*\\|",
                        "${1}" + pd_3info + "\\|",
                        "",
                        false));
          } else {
            listTags =
                Casting.toInteger(
                    Regex.substitution(
                        Casting.toString(listTags),
                        "(" + Tag.get(n1) + "_" + n1 + "_<)",
                        "${1}" + pd_3info + "\\|",
                        "",
                        false));
            ATTR.get(n1).put(atr, value);
            if (Pd.compare(Pd.checkNull(atr), "lemma") == 0) {
              Lemma.set(n1, ATTR.get(n1).get("lemma"));
            }
            if (Pd.compare(Pd.checkNull(atr), "token") == 0) {
              Token.set(n1, ATTR.get(n1).get("token"));
            }
          }
        }
      }
    }
    return new Box[0];
  }

  public static Box[] Inherit(Box[] __) {
    String y;
    String z;
    String[] x;
    y = Casting.toString(__[0]);
    z = Casting.toString(__[1]);
    x =
        (String[])
            Casting.cast(
                new Casting() {
                  @Override
                  public Object casting(Object arg) {
                    if (arg == null) {
                      return null;
                    }
                    Box[] col = (Box[]) arg;
                    String[] col2 = new String[col.length];
                    for (int i = 0; i < col.length; i++) {
                      col2[i] = Casting.toString(col[i]);
                    }
                    return col2;
                  }
                },
                Pd.subEquals(2, __));
    Integer n1 = 0;
    Integer n2 = 0;
    String[] pd_2y = Perl.split(",", y);
    if (Pd.compare(Pd.checkNull(z), "DepHead") == 0) {
      for (Integer m = 0;
          Pd.checkNull(m) <= Pd.checkNull((x.length - 1));
          m = Pd.checkNull(m) + 1) {
        String Dep = x[m];
        m = Pd.checkNull(m) + 1;
        String Head = x[m];
        String[] pd_46;
        pd_46 = Regex.matcher(Head, "[w]+_([0-9]+)", "", false);
        n1 = Casting.toInteger(pd_46[0]);
        String[] pd_47;
        pd_47 = Regex.matcher(Dep, "[w]+_([0-9]+)", "", false);
        n2 = Casting.toInteger(pd_47[0]);
        for (String atr : pd_2y) {
          if (!Casting.toBoolean(ATTR.get(n1).get(atr))) {
            listTags =
                Casting.toInteger(
                    Regex.substitution(
                        Casting.toString(listTags),
                        "(" + Tag.get(n1) + "_" + n1 + l + ")",
                        "${1}" + atr + ":" + ATTR.get(n2).get(atr) + "\\|",
                        "",
                        false));
          } else {
            listTags =
                Casting.toInteger(
                    Regex.substitution(
                        Casting.toString(listTags),
                        "(" + Tag.get(n1) + "_" + n1 + l + ")" + atr + ":" + ATTR.get(n1).get(atr),
                        "${1}" + atr + ":" + ATTR.get(n2).get(atr),
                        "",
                        false));
          }
          ATTR.get(n1).put(atr, ATTR.get(n2).get(atr));
        }
      }
    } else if (Pd.compare(Pd.checkNull(z), "HeadDep") == 0) {
      for (Integer m = 0;
          Pd.checkNull(m) <= Pd.checkNull((x.length - 1));
          m = Pd.checkNull(m) + 1) {
        String Head = x[m];
        m = Pd.checkNull(m) + 1;
        String Dep = x[m];
        String[] pd_48;
        pd_48 = Regex.matcher(Head, "[w]+_([0-9]+)", "", false);
        n1 = Casting.toInteger(pd_48[0]);
        String[] pd_49;
        pd_49 = Regex.matcher(Dep, "[w]+_([0-9]+)", "", false);
        n2 = Casting.toInteger(pd_49[0]);
        for (String atr : pd_2y) {
          if (!Casting.toBoolean(ATTR.get(n1).get(atr))) {
            listTags =
                Casting.toInteger(
                    Regex.substitution(
                        Casting.toString(listTags),
                        "(" + Tag.get(n1) + "_" + n1 + l + ")",
                        "${1}" + atr + ":" + ATTR.get(n2).get(atr) + "\\|",
                        "",
                        false));
          } else {
            listTags =
                Casting.toInteger(
                    Regex.substitution(
                        Casting.toString(listTags),
                        "(" + Tag.get(n1) + "_" + n1 + l + ")" + atr + ":" + ATTR.get(n1).get(atr),
                        "${1}" + atr + ":" + ATTR.get(n2).get(atr),
                        "",
                        false));
          }
          ATTR.get(n1).put(atr, ATTR.get(n2).get(atr));
        }
      }
    } else if (Pd.compare(Pd.checkNull(z), "DepRelHead") == 0) {
      for (Integer m = 0;
          Pd.checkNull(m) <= Pd.checkNull((x.length - 1));
          m = Pd.checkNull(m) + 1) {
        String Dep = x[m];
        m = Pd.checkNull(m) + 2;
        String Head = x[m];
        String[] pd_50;
        pd_50 = Regex.matcher(Head, "[w]+_([0-9]+)", "", false);
        n1 = Casting.toInteger(pd_50[0]);
        String[] pd_51;
        pd_51 = Regex.matcher(Dep, "[w]+_([0-9]+)", "", false);
        n2 = Casting.toInteger(pd_51[0]);
        for (String atr : pd_2y) {
          if (!Casting.toBoolean(ATTR.get(n1).get(atr))) {
            listTags =
                Casting.toInteger(
                    Regex.substitution(
                        Casting.toString(listTags),
                        "(" + Tag.get(n1) + "_" + n1 + l + ")",
                        "${1}" + atr + ":" + ATTR.get(n2).get(atr) + "\\|",
                        "",
                        false));
          } else {
            listTags =
                Casting.toInteger(
                    Regex.substitution(
                        Casting.toString(listTags),
                        "(" + Tag.get(n1) + "_" + n1 + l + ")" + atr + ":" + ATTR.get(n1).get(atr),
                        "${1}" + atr + ":" + ATTR.get(n2).get(atr),
                        "",
                        false));
          }
          ATTR.get(n1).put(atr, ATTR.get(n2).get(atr));
        }
      }
    } else if (Pd.compare(Pd.checkNull(z), "HeadRelDep") == 0) {
      for (Integer m = 0;
          Pd.checkNull(m) <= Pd.checkNull((x.length - 1));
          m = Pd.checkNull(m) + 1) {
        String Head = x[m];
        m = Pd.checkNull(m) + 2;
        String Dep = x[m];
        String[] pd_52;
        pd_52 = Regex.matcher(Head, "[w]+_([0-9]+)", "", false);
        n1 = Casting.toInteger(pd_52[0]);
        String[] pd_53;
        pd_53 = Regex.matcher(Dep, "[w]+_([0-9]+)", "", false);
        n2 = Casting.toInteger(pd_53[0]);
        for (String atr : pd_2y) {
          if (!Casting.toBoolean(ATTR.get(n1).get(atr))) {
            listTags =
                Casting.toInteger(
                    Regex.substitution(
                        Casting.toString(listTags),
                        "(" + Tag.get(n1) + "_" + n1 + l + ")",
                        "${1}" + atr + ":" + ATTR.get(n2).get(atr) + "\\|",
                        "",
                        false));
          } else {
            listTags =
                Casting.toInteger(
                    Regex.substitution(
                        Casting.toString(listTags),
                        "(" + Tag.get(n1) + "_" + n1 + l + ")" + atr + ":" + ATTR.get(n1).get(atr),
                        "${1}" + atr + ":" + ATTR.get(n2).get(atr),
                        "",
                        false));
          }
          ATTR.get(n1).put(atr, ATTR.get(n2).get(atr));
        }
      }
    } else if (Pd.compare(Pd.checkNull(z), "RelDepHead") == 0) {
      for (Integer m = 0;
          Pd.checkNull(m) <= Pd.checkNull((x.length - 1));
          m = Pd.checkNull(m) + 1) {
        m = Pd.checkNull(m) + 1;
        String Dep = x[m];
        m = Pd.checkNull(m) + 1;
        String Head = x[m];
        String[] pd_54;
        pd_54 = Regex.matcher(Head, "[w]+_([0-9]+)", "", false);
        n1 = Casting.toInteger(pd_54[0]);
        String[] pd_55;
        pd_55 = Regex.matcher(Dep, "[w]+_([0-9]+)", "", false);
        n2 = Casting.toInteger(pd_55[0]);
        for (String atr : pd_2y) {
          if (!Casting.toBoolean(ATTR.get(n1).get(atr))) {
            listTags =
                Casting.toInteger(
                    Regex.substitution(
                        Casting.toString(listTags),
                        "(" + Tag.get(n1) + "_" + n1 + l + ")",
                        "${1}" + atr + ":" + ATTR.get(n2).get(atr) + "\\|",
                        "",
                        false));
          } else {
            listTags =
                Casting.toInteger(
                    Regex.substitution(
                        Casting.toString(listTags),
                        "(" + Tag.get(n1) + "_" + n1 + l + ")" + atr + ":" + ATTR.get(n1).get(atr),
                        "${1}" + atr + ":" + ATTR.get(n2).get(atr),
                        "",
                        false));
          }
          ATTR.get(n1).put(atr, ATTR.get(n2).get(atr));
        }
      }
    } else if (Pd.compare(Pd.checkNull(z), "RelHeadDep") == 0) {
      for (Integer m = 0;
          Pd.checkNull(m) <= Pd.checkNull((x.length - 1));
          m = Pd.checkNull(m) + 1) {
        m = Pd.checkNull(m) + 1;
        String Head = x[m];
        m = Pd.checkNull(m) + 1;
        String Dep = x[m];
        String[] pd_56;
        pd_56 = Regex.matcher(Head, "[w]+_([0-9]+)", "", false);
        n1 = Casting.toInteger(pd_56[0]);
        String[] pd_57;
        pd_57 = Regex.matcher(Dep, "[w]+_([0-9]+)", "", false);
        n2 = Casting.toInteger(pd_57[0]);
        for (String atr : pd_2y) {
          if (!Casting.toBoolean(ATTR.get(n1).get(atr))) {
            listTags =
                Casting.toInteger(
                    Regex.substitution(
                        Casting.toString(listTags),
                        "(" + Tag.get(n1) + "_" + n1 + l + ")",
                        "${1}" + atr + ":" + ATTR.get(n2).get(atr) + "\\|",
                        "",
                        false));
          } else {
            listTags =
                Casting.toInteger(
                    Regex.substitution(
                        Casting.toString(listTags),
                        "(" + Tag.get(n1) + "_" + n1 + l + ")" + atr + ":" + ATTR.get(n1).get(atr),
                        "${1}" + atr + ":" + ATTR.get(n2).get(atr),
                        "",
                        false));
          }
          ATTR.get(n1).put(atr, ATTR.get(n2).get(atr));
        }
      }
    } else if (Pd.compare(Pd.checkNull(z), "DepHeadRel") == 0) {
      for (Integer m = 0;
          Pd.checkNull(m) <= Pd.checkNull((x.length - 1));
          m = Pd.checkNull(m) + 1) {
        String Dep = x[m];
        m = Pd.checkNull(m) + 1;
        String Head = x[m];
        String[] pd_58;
        pd_58 = Regex.matcher(Head, "[w]+_([0-9]+)", "", false);
        n1 = Casting.toInteger(pd_58[0]);
        String[] pd_59;
        pd_59 = Regex.matcher(Dep, "[w]+_([0-9]+)", "", false);
        n2 = Casting.toInteger(pd_59[0]);
        for (String atr : pd_2y) {
          if (!Casting.toBoolean(ATTR.get(n1).get(atr))) {
            listTags =
                Casting.toInteger(
                    Regex.substitution(
                        Casting.toString(listTags),
                        "(" + Tag.get(n1) + "_" + n1 + l + ")",
                        "${1}" + atr + ":" + ATTR.get(n2).get(atr) + "\\|",
                        "",
                        false));
          } else {
            listTags =
                Casting.toInteger(
                    Regex.substitution(
                        Casting.toString(listTags),
                        "(" + Tag.get(n1) + "_" + n1 + l + ")" + atr + ":" + ATTR.get(n1).get(atr),
                        "${1}" + atr + ":" + ATTR.get(n2).get(atr),
                        "",
                        false));
          }
          ATTR.get(n1).put(atr, ATTR.get(n2).get(atr));
        }
      }
    } else if (Pd.compare(Pd.checkNull(z), "HeadDepRel") == 0) {
      for (Integer m = 0;
          Pd.checkNull(m) <= Pd.checkNull((x.length - 1));
          m = Pd.checkNull(m) + 1) {
        String Head = x[m];
        m = Pd.checkNull(m) + 1;
        String Dep = x[m];
        String[] pd_60;
        pd_60 = Regex.matcher(Head, "[w]+_([0-9]+)", "", false);
        n1 = Casting.toInteger(pd_60[0]);
        String[] pd_61;
        pd_61 = Regex.matcher(Dep, "[w]+_([0-9]+)", "", false);
        n2 = Casting.toInteger(pd_61[0]);
        for (String atr : pd_2y) {
          if (!Casting.toBoolean(ATTR.get(n1).get(atr))) {
            listTags =
                Casting.toInteger(
                    Regex.substitution(
                        Casting.toString(listTags),
                        "(" + Tag.get(n1) + "_" + n1 + l + ")",
                        "${1}" + atr + ":" + ATTR.get(n2).get(atr) + "\\|",
                        "",
                        false));
          } else {
            listTags =
                Casting.toInteger(
                    Regex.substitution(
                        Casting.toString(listTags),
                        "(" + Tag.get(n1) + "_" + n1 + l + ")" + atr + ":" + ATTR.get(n1).get(atr),
                        "${1}" + atr + ":" + ATTR.get(n2).get(atr),
                        "",
                        false));
          }
          ATTR.get(n1).put(atr, ATTR.get(n2).get(atr));
        }
      }
    } else if (Pd.compare(Pd.checkNull(z), "DepHead_lex") == 0) {
      for (Integer m = 0;
          Pd.checkNull(m) <= Pd.checkNull((x.length - 1));
          m = Pd.checkNull(m) + 1) {
        String Dep = x[m];
        m = Pd.checkNull(m) + 1;
        String Head = x[m];
        String[] pd_62;
        pd_62 = Regex.matcher(Head, "[w]+_([0-9]+)", "", false);
        n1 = Casting.toInteger(pd_62[0]);
        String[] pd_63;
        pd_63 = Regex.matcher(Dep, "[w]+_([0-9]+)", "", false);
        n2 = Casting.toInteger(pd_63[0]);
        for (String atr : pd_2y) {
          if (!Casting.toBoolean(ATTR.get(n1).get(atr))) {
            listTags =
                Casting.toInteger(
                    Regex.substitution(
                        Casting.toString(listTags),
                        "(" + Tag.get(n1) + "_" + n1 + l + ")",
                        "${1}" + atr + ":" + ATTR.get(n2).get(atr) + "\\|",
                        "",
                        false));
          } else {
            listTags =
                Casting.toInteger(
                    Regex.substitution(
                        Casting.toString(listTags),
                        "(" + Tag.get(n1) + "_" + n1 + l + ")" + atr + ":" + ATTR.get(n1).get(atr),
                        "${1}" + atr + ":" + ATTR.get(n2).get(atr),
                        "",
                        false));
          }
          ATTR.get(n1).put(atr, ATTR.get(n2).get(atr));
        }
      }
    } else if (Pd.compare(Pd.checkNull(z), "HeadDep_lex") == 0) {
      for (Integer m = 0;
          Pd.checkNull(m) <= Pd.checkNull((x.length - 1));
          m = Pd.checkNull(m) + 1) {
        String Head = x[m];
        m = Pd.checkNull(m) + 1;
        String Dep = x[m];
        String[] pd_64;
        pd_64 = Regex.matcher(Head, "[w]+_([0-9]+)", "", false);
        n1 = Casting.toInteger(pd_64[0]);
        String[] pd_65;
        pd_65 = Regex.matcher(Dep, "[w]+_([0-9]+)", "", false);
        n2 = Casting.toInteger(pd_65[0]);
        for (String atr : pd_2y) {
          if (!Casting.toBoolean(ATTR.get(n1).get(atr))) {
            listTags =
                Casting.toInteger(
                    Regex.substitution(
                        Casting.toString(listTags),
                        "(" + Tag.get(n1) + "_" + n1 + l + ")",
                        "${1}" + atr + ":" + ATTR.get(n2).get(atr) + "\\|",
                        "",
                        false));
          } else {
            listTags =
                Casting.toInteger(
                    Regex.substitution(
                        Casting.toString(listTags),
                        "(" + Tag.get(n1) + "_" + n1 + l + ")" + atr + ":" + ATTR.get(n1).get(atr),
                        "${1}" + atr + ":" + ATTR.get(n2).get(atr),
                        "",
                        false));
          }
          ATTR.get(n1).put(atr, ATTR.get(n2).get(atr));
        }
      }
    }
    return new Box[0];
  }

  public static Box[] Add(Box[] __) {
    String z;
    String y;
    String[] x;
    z = Casting.toString(__[0]);
    y = Casting.toString(__[1]);
    x =
        (String[])
            Casting.cast(
                new Casting() {
                  @Override
                  public Object casting(Object arg) {
                    if (arg == null) {
                      return null;
                    }
                    Box[] col = (Box[]) arg;
                    String[] col2 = new String[col.length];
                    for (int i = 0; i < col.length; i++) {
                      col2[i] = Casting.toString(col[i]);
                    }
                    return col2;
                  }
                },
                Pd.subEquals(2, __));
    Integer n1 = 0;
    Integer n2 = 0;
    String atr = "";
    String value = "";
    String pd_4info = "";
    String[] pd_3y = Perl.split(",", y);
    if (Pd.compare(Pd.checkNull(z), "Head") == 0) {
      for (Integer m = 0;
          Pd.checkNull(m) <= Pd.checkNull((x.length - 1));
          m = Pd.checkNull(m) + 1) {
        String Head = x[m];
        String[] pd_66;
        pd_66 = Regex.matcher(Head, "[w]+_([0-9]+)", "", false);
        n1 = Casting.toInteger(pd_66[0]);
        for (String pd_5info : pd_3y) {
          String[] pd_67;
          pd_67 = Perl.split(":", pd_5info);
          atr = pd_67[0];
          value = pd_67[1];
          if (Regex.match(
              Casting.toString(listTags), Tag.get(n1) + "_" + n1 + l + atr + ":", "", false)) {
            ATTR.get(n1).put(atr, value);
            if (Pd.compare(Pd.checkNull(atr), "lemma") == 0) {
              Lemma.set(n1, ATTR.get(n1).get("lemma"));
            }
            if (Pd.compare(Pd.checkNull(atr), "token") == 0) {
              Token.set(n1, ATTR.get(n1).get("token"));
            }
            listTags =
                Casting.toInteger(
                    Regex.substitution(
                        Casting.toString(listTags),
                        "(" + Tag.get(n1) + "_" + n1 + l + ")" + atr + ":[^|]*\\|",
                        "${1}" + pd_5info + "\\|",
                        "",
                        false));
          } else {
            listTags =
                Casting.toInteger(
                    Regex.substitution(
                        Casting.toString(listTags),
                        "(" + Tag.get(n1) + "_" + n1 + "_<)",
                        "${1}" + pd_5info + "\\|",
                        "",
                        false));
            ATTR.get(n1).put(atr, value);
            if (Pd.compare(Pd.checkNull(atr), "lemma") == 0) {
              Lemma.set(n1, ATTR.get(n1).get("lemma"));
            }
            if (Pd.compare(Pd.checkNull(atr), "token") == 0) {
              Token.set(n1, ATTR.get(n1).get("token"));
            }
          }
        }
      }
    } else if (Pd.compare(Pd.checkNull(z), "DepHead") == 0) {
      for (Integer m = 0;
          Pd.checkNull(m) <= Pd.checkNull((x.length - 1));
          m = Pd.checkNull(m) + 1) {
        String Dep = x[m];
        m = Pd.checkNull(m) + 1;
        String Head = x[m];
        String[] pd_68;
        pd_68 = Regex.matcher(Head, "[w]+_([0-9]+)", "", false);
        n1 = Casting.toInteger(pd_68[0]);
        String[] pd_69;
        pd_69 = Regex.matcher(Dep, "[w]+_([0-9]+)", "", false);
        n2 = Casting.toInteger(pd_69[0]);
        for (String pd_6info : pd_3y) {
          String[] pd_70;
          pd_70 = Perl.split(":", pd_6info);
          atr = pd_70[0];
          value = pd_70[1];
          /*#change the PoS tag:*/
          if (Regex.match(atr, "^tag", "", false)) {
            ATTR.get(n1).put(atr, value);
            listTags =
                Casting.toInteger(
                    Regex.substitution(Casting.toString(listTags), Tag.get(n1), value, "", false));
            Tag.set(n1, value);
          } else if (Regex.match(
              Casting.toString(listTags), Tag.get(n1) + "_" + n1 + l + atr + ":", "", false)) {
            ATTR.get(n1).put(atr, value);
            if (Pd.compare(Pd.checkNull(atr), "lemma") == 0) {
              Lemma.set(n1, ATTR.get(n1).get("lemma"));
            }
            if (Pd.compare(Pd.checkNull(atr), "token") == 0) {
              Token.set(n1, ATTR.get(n1).get("token"));
            }
            listTags =
                Casting.toInteger(
                    Regex.substitution(
                        Casting.toString(listTags),
                        "(" + Tag.get(n1) + "_" + n1 + l + ")" + atr + ":[^|]*\\|",
                        "${1}" + pd_6info + "\\|",
                        "",
                        false));
            /* print STDERR "$atr - $value : #$l# - #$r#";*/
          } else {
            listTags =
                Casting.toInteger(
                    Regex.substitution(
                        Casting.toString(listTags),
                        "(" + Tag.get(n1) + "_" + n1 + "_<)",
                        "${1}" + pd_6info + "\\|",
                        "",
                        false));
            ATTR.get(n1).put(atr, value);
            if (Pd.compare(Pd.checkNull(atr), "lemma") == 0) {
              Lemma.set(n1, ATTR.get(n1).get("lemma"));
            }
            if (Pd.compare(Pd.checkNull(atr), "token") == 0) {
              Token.set(n1, ATTR.get(n1).get("token"));
            }
            /*print STDERR "$atr - $value ::: #$l# - #$r#";*/
          }
        }
      }
    } else if (Pd.compare(Pd.checkNull(z), "HeadDep") == 0) {
      for (Integer m = 0;
          Pd.checkNull(m) <= Pd.checkNull((x.length - 1));
          m = Pd.checkNull(m) + 1) {
        String Head = x[m];
        m = Pd.checkNull(m) + 1;
        String Dep = x[m];
        String[] pd_71;
        pd_71 = Regex.matcher(Head, "[w]+_([0-9]+)", "", false);
        n1 = Casting.toInteger(pd_71[0]);
        String[] pd_72;
        pd_72 = Regex.matcher(Dep, "[w]+_([0-9]+)", "", false);
        n2 = Casting.toInteger(pd_72[0]);
        for (String pd_7info : pd_3y) {
          String[] pd_73;
          pd_73 = Perl.split(":", pd_7info);
          atr = pd_73[0];
          value = pd_73[1];
          /*#change the PoS tag:*/
          if (Regex.match(atr, "^tag", "", false)) {
            ATTR.get(n1).put(atr, value);
            listTags =
                Casting.toInteger(
                    Regex.substitution(Casting.toString(listTags), Tag.get(n1), value, "", false));
            Tag.set(n1, value);
          } else if (Regex.match(
              Casting.toString(listTags), Tag.get(n1) + "_" + n1 + l + atr + ":", "", false)) {
            ATTR.get(n1).put(atr, value);
            if (Pd.compare(Pd.checkNull(atr), "lemma") == 0) {
              Lemma.set(n1, ATTR.get(n1).get("lemma"));
            }
            if (Pd.compare(Pd.checkNull(atr), "token") == 0) {
              Token.set(n1, ATTR.get(n1).get("token"));
            }
            listTags =
                Casting.toInteger(
                    Regex.substitution(
                        Casting.toString(listTags),
                        "(" + Tag.get(n1) + "_" + n1 + l + ")" + atr + ":[^|]*\\|",
                        "${1}" + pd_7info + "\\|",
                        "",
                        false));
          } else {
            listTags =
                Casting.toInteger(
                    Regex.substitution(
                        Casting.toString(listTags),
                        "(" + Tag.get(n1) + "_" + n1 + "_<)",
                        "${1}" + pd_7info + "\\|",
                        "",
                        false));
            ATTR.get(n1).put(atr, value);
            if (Pd.compare(Pd.checkNull(atr), "lemma") == 0) {
              Lemma.set(n1, ATTR.get(n1).get("lemma"));
            }
            if (Pd.compare(Pd.checkNull(atr), "token") == 0) {
              Token.set(n1, ATTR.get(n1).get("token"));
            }
          }
        }
      }
    } else if (Pd.compare(Pd.checkNull(z), "DepRelHead") == 0) {
      for (Integer m = 0;
          Pd.checkNull(m) <= Pd.checkNull((x.length - 1));
          m = Pd.checkNull(m) + 1) {
        String Dep = x[m];
        m = Pd.checkNull(m) + 2;
        String Head = x[m];
        String[] pd_74;
        pd_74 = Regex.matcher(Head, "[w]+_([0-9]+)", "", false);
        n1 = Casting.toInteger(pd_74[0]);
        String[] pd_75;
        pd_75 = Regex.matcher(Dep, "[w]+_([0-9]+)", "", false);
        n2 = Casting.toInteger(pd_75[0]);
        for (String pd_8info : pd_3y) {
          String[] pd_76;
          pd_76 = Perl.split(":", pd_8info);
          atr = pd_76[0];
          value = pd_76[1];
          /*#change the PoS tag:*/
          if (Regex.match(atr, "^tag", "", false)) {
            ATTR.get(n1).put(atr, value);
            listTags =
                Casting.toInteger(
                    Regex.substitution(Casting.toString(listTags), Tag.get(n1), value, "", false));
            Tag.set(n1, value);
          } else if (Regex.match(
              Casting.toString(listTags),
              "(" + Tag.get(n1) + "_" + n1 + l + ")" + atr + ":",
              "",
              false)) {
            ATTR.get(n1).put(atr, value);
            if (Pd.compare(Pd.checkNull(atr), "lemma") == 0) {
              Lemma.set(n1, ATTR.get(n1).get("lemma"));
            }
            if (Pd.compare(Pd.checkNull(atr), "token") == 0) {
              Token.set(n1, ATTR.get(n1).get("token"));
            }
            listTags =
                Casting.toInteger(
                    Regex.substitution(
                        Casting.toString(listTags),
                        "(" + Tag.get(n1) + "_" + n1 + l + ")" + atr + ":[^|]*\\|",
                        "${1}" + pd_8info + "\\|",
                        "",
                        false));
          } else {
            listTags =
                Casting.toInteger(
                    Regex.substitution(
                        Casting.toString(listTags),
                        "(" + Tag.get(n1) + "_" + n1 + "_<)",
                        "${1}" + pd_8info + "\\|",
                        "",
                        false));
            ATTR.get(n1).put(atr, value);
            if (Pd.compare(Pd.checkNull(atr), "lemma") == 0) {
              Lemma.set(n1, ATTR.get(n1).get("lemma"));
            }
            if (Pd.compare(Pd.checkNull(atr), "token") == 0) {
              Token.set(n1, ATTR.get(n1).get("token"));
            }
          }
        }
      }
    } else if (Pd.compare(Pd.checkNull(z), "HeadRelDep") == 0) {
      for (Integer m = 0;
          Pd.checkNull(m) <= Pd.checkNull((x.length - 1));
          m = Pd.checkNull(m) + 1) {
        String Head = x[m];
        m = Pd.checkNull(m) + 2;
        String Dep = x[m];
        String[] pd_77;
        pd_77 = Regex.matcher(Head, "[w]+_([0-9]+)", "", false);
        n1 = Casting.toInteger(pd_77[0]);
        String[] pd_78;
        pd_78 = Regex.matcher(Dep, "[w]+_([0-9]+)", "", false);
        n2 = Casting.toInteger(pd_78[0]);
        for (String pd_80 : pd_3y) {
          pd_4info = pd_80;
          String[] pd_79;
          pd_79 = Perl.split(":", pd_4info);
          atr = pd_79[0];
          value = pd_79[1];
          /*#change the PoS tag:*/
          if (Regex.match(atr, "^tag", "", false)) {
            ATTR.get(n1).put(atr, value);
            listTags =
                Casting.toInteger(
                    Regex.substitution(Casting.toString(listTags), Tag.get(n1), value, "", false));
            Tag.set(n1, value);
          } else if (Regex.match(
              Casting.toString(listTags),
              "(" + Tag.get(n1) + "_" + n1 + l + ")" + atr + ":",
              "",
              false)) {
            ATTR.get(n1).put(atr, value);
            if (Pd.compare(Pd.checkNull(atr), "lemma") == 0) {
              Lemma.set(n1, ATTR.get(n1).get("lemma"));
            }
            if (Pd.compare(Pd.checkNull(atr), "token") == 0) {
              Token.set(n1, ATTR.get(n1).get("token"));
            }
            listTags =
                Casting.toInteger(
                    Regex.substitution(
                        Casting.toString(listTags),
                        "(" + Tag.get(n1) + "_" + n1 + l + ")" + atr + ":[^|]*\\|",
                        "${1}" + pd_4info + "\\|",
                        "",
                        false));
          } else {
            listTags =
                Casting.toInteger(
                    Regex.substitution(
                        Casting.toString(listTags),
                        "(" + Tag.get(n1) + "_" + n1 + "_<)",
                        "${1}" + pd_4info + "\\|",
                        "",
                        false));
            ATTR.get(n1).put(atr, value);
            if (Pd.compare(Pd.checkNull(atr), "lemma") == 0) {
              Lemma.set(n1, ATTR.get(n1).get("lemma"));
            }
            if (Pd.compare(Pd.checkNull(atr), "token") == 0) {
              Token.set(n1, ATTR.get(n1).get("token"));
            }
          }
        }
      }
    } else if (Pd.compare(Pd.checkNull(z), "RelDepHead") == 0) {
      for (Integer m = 0;
          Pd.checkNull(m) <= Pd.checkNull((x.length - 1));
          m = Pd.checkNull(m) + 1) {
        m = Pd.checkNull(m) + 1;
        String Dep = x[m];
        m = Pd.checkNull(m) + 1;
        String Head = x[m];
        String[] pd_81;
        pd_81 = Regex.matcher(Head, "[w]+_([0-9]+)", "", false);
        n1 = Casting.toInteger(pd_81[0]);
        String[] pd_82;
        pd_82 = Regex.matcher(Dep, "[w]+_([0-9]+)", "", false);
        n2 = Casting.toInteger(pd_82[0]);
        for (String pd_9info : pd_3y) {
          String[] pd_83;
          pd_83 = Perl.split(":", pd_9info);
          atr = pd_83[0];
          value = pd_83[1];
          /*#change the PoS tag:*/
          if (Regex.match(atr, "^tag", "", false)) {
            ATTR.get(n1).put(atr, value);
            listTags =
                Casting.toInteger(
                    Regex.substitution(Casting.toString(listTags), Tag.get(n1), value, "", false));
            Tag.set(n1, value);
          } else if (Regex.match(
              Casting.toString(listTags),
              "(" + Tag.get(n1) + "_" + n1 + l + ")" + atr + ":",
              "",
              false)) {
            ATTR.get(n1).put(atr, value);
            if (Pd.compare(Pd.checkNull(atr), "lemma") == 0) {
              Lemma.set(n1, ATTR.get(n1).get("lemma"));
            }
            if (Pd.compare(Pd.checkNull(atr), "token") == 0) {
              Token.set(n1, ATTR.get(n1).get("token"));
            }
            listTags =
                Casting.toInteger(
                    Regex.substitution(
                        Casting.toString(listTags),
                        "(" + Tag.get(n1) + "_" + n1 + l + ")" + atr + ":[^|]*\\|",
                        "${1}" + pd_9info + "\\|",
                        "",
                        false));
          } else {
            listTags =
                Casting.toInteger(
                    Regex.substitution(
                        Casting.toString(listTags),
                        "(" + Tag.get(n1) + "_" + n1 + "_<)",
                        "${1}" + pd_9info + "\\|",
                        "",
                        false));
            ATTR.get(n1).put(atr, value);
            if (Pd.compare(Pd.checkNull(atr), "lemma") == 0) {
              Lemma.set(n1, ATTR.get(n1).get("lemma"));
            }
            if (Pd.compare(Pd.checkNull(atr), "token") == 0) {
              Token.set(n1, ATTR.get(n1).get("token"));
            }
          }
        }
      }
    } else if (Pd.compare(Pd.checkNull(z), "RelHeadDep") == 0) {
      for (Integer m = 0;
          Pd.checkNull(m) <= Pd.checkNull((x.length - 1));
          m = Pd.checkNull(m) + 1) {
        m = Pd.checkNull(m) + 1;
        String Head = x[m];
        m = Pd.checkNull(m) + 1;
        String Dep = x[m];
        String[] pd_84;
        pd_84 = Regex.matcher(Head, "[w]+_([0-9]+)", "", false);
        n1 = Casting.toInteger(pd_84[0]);
        String[] pd_85;
        pd_85 = Regex.matcher(Dep, "[w]+_([0-9]+)", "", false);
        n2 = Casting.toInteger(pd_85[0]);
        for (String pd_87 : pd_3y) {
          pd_4info = pd_87;
          String[] pd_86;
          pd_86 = Perl.split(":", pd_4info);
          atr = pd_86[0];
          value = pd_86[1];
          /*#change the PoS tag:*/
          if (Regex.match(atr, "^tag", "", false)) {
            ATTR.get(n1).put(atr, value);
            listTags =
                Casting.toInteger(
                    Regex.substitution(Casting.toString(listTags), Tag.get(n1), value, "", false));
            Tag.set(n1, value);
          } else if (Regex.match(
              Casting.toString(listTags),
              "(" + Tag.get(n1) + "_" + n1 + l + ")" + atr + ":",
              "",
              false)) {
            ATTR.get(n1).put(atr, value);
            if (Pd.compare(Pd.checkNull(atr), "lemma") == 0) {
              Lemma.set(n1, ATTR.get(n1).get("lemma"));
            }
            if (Pd.compare(Pd.checkNull(atr), "token") == 0) {
              Token.set(n1, ATTR.get(n1).get("token"));
            }
            listTags =
                Casting.toInteger(
                    Regex.substitution(
                        Casting.toString(listTags),
                        "(" + Tag.get(n1) + "_" + n1 + l + ")" + atr + ":[^|]*\\|",
                        "${1}" + pd_4info + "\\|",
                        "",
                        false));
          } else {
            listTags =
                Casting.toInteger(
                    Regex.substitution(
                        Casting.toString(listTags),
                        "(" + Tag.get(n1) + "_" + n1 + "_<)",
                        "${1}" + pd_4info + "\\|",
                        "",
                        false));
            ATTR.get(n1).put(atr, value);
            if (Pd.compare(Pd.checkNull(atr), "lemma") == 0) {
              Lemma.set(n1, ATTR.get(n1).get("lemma"));
            }
            if (Pd.compare(Pd.checkNull(atr), "token") == 0) {
              Token.set(n1, ATTR.get(n1).get("token"));
            }
          }
        }
      }
    } else if (Pd.compare(Pd.checkNull(z), "DepHeadRel") == 0) {
      for (Integer m = 0;
          Pd.checkNull(m) <= Pd.checkNull((x.length - 1));
          m = Pd.checkNull(m) + 1) {
        String Dep = x[m];
        m = Pd.checkNull(m) + 1;
        String Head = x[m];
        String[] pd_88;
        pd_88 = Regex.matcher(Head, "[w]+_([0-9]+)", "", false);
        n1 = Casting.toInteger(pd_88[0]);
        String[] pd_89;
        pd_89 = Regex.matcher(Dep, "[w]+_([0-9]+)", "", false);
        n2 = Casting.toInteger(pd_89[0]);
        for (String pd_91 : pd_3y) {
          pd_4info = pd_91;
          String[] pd_90;
          pd_90 = Perl.split(":", pd_4info);
          atr = pd_90[0];
          value = pd_90[1];
          /*#change the PoS tag:*/
          if (Regex.match(atr, "^tag", "", false)) {
            ATTR.get(n1).put(atr, value);
            listTags =
                Casting.toInteger(
                    Regex.substitution(Casting.toString(listTags), Tag.get(n1), value, "", false));
            Tag.set(n1, value);
          } else if (Regex.match(
              Casting.toString(listTags),
              "(" + Tag.get(n1) + "_" + n1 + l + ")" + atr + ":",
              "",
              false)) {
            ATTR.get(n1).put(atr, value);
            if (Pd.compare(Pd.checkNull(atr), "lemma") == 0) {
              Lemma.set(n1, ATTR.get(n1).get("lemma"));
            }
            if (Pd.compare(Pd.checkNull(atr), "token") == 0) {
              Token.set(n1, ATTR.get(n1).get("token"));
            }
            listTags =
                Casting.toInteger(
                    Regex.substitution(
                        Casting.toString(listTags),
                        "(" + Tag.get(n1) + "_" + n1 + l + ")" + atr + ":[^|]*\\|",
                        "${1}" + pd_4info + "\\|",
                        "",
                        false));
          } else {
            listTags =
                Casting.toInteger(
                    Regex.substitution(
                        Casting.toString(listTags),
                        "(" + Tag.get(n1) + "_" + n1 + "_<)",
                        "${1}" + pd_4info + "\\|",
                        "",
                        false));
            ATTR.get(n1).put(atr, value);
            if (Pd.compare(Pd.checkNull(atr), "lemma") == 0) {
              Lemma.set(n1, ATTR.get(n1).get("lemma"));
            }
            if (Pd.compare(Pd.checkNull(atr), "token") == 0) {
              Token.set(n1, ATTR.get(n1).get("token"));
            }
          }
        }
      }
    } else if (Pd.compare(Pd.checkNull(z), "HeadDepRel") == 0) {
      for (Integer m = 0;
          Pd.checkNull(m) <= Pd.checkNull((x.length - 1));
          m = Pd.checkNull(m) + 1) {
        String Head = x[m];
        m = Pd.checkNull(m) + 1;
        String Dep = x[m];
        String[] pd_92;
        pd_92 = Regex.matcher(Head, "[w]+_([0-9]+)", "", false);
        n1 = Casting.toInteger(pd_92[0]);
        String[] pd_93;
        pd_93 = Regex.matcher(Dep, "[w]+_([0-9]+)", "", false);
        n2 = Casting.toInteger(pd_93[0]);
        for (String pd_95 : pd_3y) {
          pd_4info = pd_95;
          String[] pd_94;
          pd_94 = Perl.split(":", pd_4info);
          atr = pd_94[0];
          value = pd_94[1];
          /*#change the PoS tag:*/
          if (Regex.match(atr, "^tag", "", false)) {
            ATTR.get(n1).put(atr, value);
            listTags =
                Casting.toInteger(
                    Regex.substitution(Casting.toString(listTags), Tag.get(n1), value, "", false));
            Tag.set(n1, value);
          } else if (Regex.match(
              Casting.toString(listTags),
              "(" + Tag.get(n1) + "_" + n1 + l + ")" + atr + ":",
              "",
              false)) {
            ATTR.get(n1).put(atr, value);
            if (Pd.compare(Pd.checkNull(atr), "lemma") == 0) {
              Lemma.set(n1, ATTR.get(n1).get("lemma"));
            }
            if (Pd.compare(Pd.checkNull(atr), "token") == 0) {
              Token.set(n1, ATTR.get(n1).get("token"));
            }
            listTags =
                Casting.toInteger(
                    Regex.substitution(
                        Casting.toString(listTags),
                        "(" + Tag.get(n1) + "_" + n1 + l + ")" + atr + ":[^|]*\\|",
                        "${1}" + pd_4info + "\\|",
                        "",
                        false));
          } else {
            listTags =
                Casting.toInteger(
                    Regex.substitution(
                        Casting.toString(listTags),
                        "(" + Tag.get(n1) + "_" + n1 + "_<)",
                        "${1}" + pd_4info + "\\|",
                        "",
                        false));
            ATTR.get(n1).put(atr, value);
            if (Pd.compare(Pd.checkNull(atr), "lemma") == 0) {
              Lemma.set(n1, ATTR.get(n1).get("lemma"));
            }
            if (Pd.compare(Pd.checkNull(atr), "token") == 0) {
              Token.set(n1, ATTR.get(n1).get("token"));
            }
          }
        }
      }
    } else if (Pd.compare(Pd.checkNull(z), "DepHead_lex") == 0) {
      for (Integer m = 0;
          Pd.checkNull(m) <= Pd.checkNull((x.length - 1));
          m = Pd.checkNull(m) + 1) {
        String Dep = x[m];
        m = Pd.checkNull(m) + 1;
        String Head = x[m];
        String[] pd_96;
        pd_96 = Regex.matcher(Head, "[w]+_([0-9]+)", "", false);
        n1 = Casting.toInteger(pd_96[0]);
        String[] pd_97;
        pd_97 = Regex.matcher(Dep, "[w]+_([0-9]+)", "", false);
        n2 = Casting.toInteger(pd_97[0]);
        for (String pd_99 : pd_3y) {
          pd_4info = pd_99;
          String[] pd_98;
          pd_98 = Perl.split(":", pd_4info);
          atr = pd_98[0];
          value = pd_98[1];
          /*#change the PoS tag:*/
          if (Regex.match(atr, "^tag", "", false)) {
            ATTR.get(n1).put(atr, value);
            listTags =
                Casting.toInteger(
                    Regex.substitution(Casting.toString(listTags), Tag.get(n1), value, "", false));
            Tag.set(n1, value);
          } else if (Regex.match(
              Casting.toString(listTags), Tag.get(n1) + "_" + n1 + l + atr + ":", "", false)) {
            ATTR.get(n1).put(atr, value);
            if (Pd.compare(Pd.checkNull(atr), "lemma") == 0) {
              Lemma.set(n1, ATTR.get(n1).get("lemma"));
            }
            if (Pd.compare(Pd.checkNull(atr), "token") == 0) {
              Token.set(n1, ATTR.get(n1).get("token"));
            }
            listTags =
                Casting.toInteger(
                    Regex.substitution(
                        Casting.toString(listTags),
                        "(" + Tag.get(n1) + "_" + n1 + l + ")" + atr + ":[^|]*\\|",
                        "${1}" + pd_4info + "\\|",
                        "",
                        false));
          } else {
            listTags =
                Casting.toInteger(
                    Regex.substitution(
                        Casting.toString(listTags),
                        "(" + Tag.get(n1) + "_" + n1 + "_<)",
                        "${1}" + pd_4info + "\\|",
                        "",
                        false));
            ATTR.get(n1).put(atr, value);
            if (Pd.compare(Pd.checkNull(atr), "lemma") == 0) {
              Lemma.set(n1, ATTR.get(n1).get("lemma"));
            }
            if (Pd.compare(Pd.checkNull(atr), "token") == 0) {
              Token.set(n1, ATTR.get(n1).get("token"));
            }
          }
        }
      }
    }
    if (Pd.compare(Pd.checkNull(z), "HeadDep_lex") == 0) {
      for (Integer m = 0;
          Pd.checkNull(m) <= Pd.checkNull((x.length - 1));
          m = Pd.checkNull(m) + 1) {
        String Head = x[m];
        m = Pd.checkNull(m) + 1;
        String Dep = x[m];
        String[] pd_100;
        pd_100 = Regex.matcher(Head, "[w]+_([0-9]+)", "", false);
        n1 = Casting.toInteger(pd_100[0]);
        String[] pd_101;
        pd_101 = Regex.matcher(Dep, "[w]+_([0-9]+)", "", false);
        n2 = Casting.toInteger(pd_101[0]);
        for (String pd_103 : pd_3y) {
          pd_4info = pd_103;
          String[] pd_102;
          pd_102 = Perl.split(":", pd_4info);
          atr = pd_102[0];
          value = pd_102[1];
          /*#change the PoS tag:*/
          if (Regex.match(atr, "^tag", "", false)) {
            ATTR.get(n1).put(atr, value);
            listTags =
                Casting.toInteger(
                    Regex.substitution(Casting.toString(listTags), Tag.get(n1), value, "", false));
            Tag.set(n1, value);
          } else if (Regex.match(
              Casting.toString(listTags), Tag.get(n1) + "_" + n1 + l + atr + ":", "", false)) {
            ATTR.get(n1).put(atr, value);
            if (Pd.compare(Pd.checkNull(atr), "lemma") == 0) {
              Lemma.set(n1, ATTR.get(n1).get("lemma"));
            }
            if (Pd.compare(Pd.checkNull(atr), "token") == 0) {
              Token.set(n1, ATTR.get(n1).get("token"));
            }
            listTags =
                Casting.toInteger(
                    Regex.substitution(
                        Casting.toString(listTags),
                        "(" + Tag.get(n1) + "_" + n1 + l + ")" + atr + ":[^|]*\\|",
                        "${1}" + pd_4info + "\\|",
                        "",
                        false));
          } else {
            listTags =
                Casting.toInteger(
                    Regex.substitution(
                        Casting.toString(listTags),
                        "(" + Tag.get(n1) + "_" + n1 + "_<)",
                        "${1}" + pd_4info + "\\|",
                        "",
                        false));
            ATTR.get(n1).put(atr, value);
            if (Pd.compare(Pd.checkNull(atr), "lemma") == 0) {
              Lemma.set(n1, ATTR.get(n1).get("lemma"));
            }
            if (Pd.compare(Pd.checkNull(atr), "token") == 0) {
              Token.set(n1, ATTR.get(n1).get("token"));
            }
            /*print STDERR "$atr - $value ::: #$l# - #$r#";*/
          }
        }
      }
    }
    return new Box[0];
  }

  public static Box[] negL(Box[] __) {
    String x = Casting.toString(__[0]);
    String expr = "";
    String ref = "";
    String CAT = "";
    String[] pd_104;
    pd_104 = Perl.split("_", x);
    CAT = pd_104[0];
    ref = pd_104[1];
    expr = "(?<!" + CAT + ")_" + ref;
    return new Box[] {Casting.box(expr)};
  }

  public static Box[] negR(Box[] __) {
    String x = Casting.toString(__[0]);
    String expr = "";
    String ref = "";
    String CAT = "";
    String[] pd_105;
    pd_105 = Perl.split("_", x);
    CAT = pd_105[0];
    ref = pd_105[1];
    expr = "?!" + CAT + "_" + ref;
    return new Box[] {Casting.box(expr)};
  }

  public static Box[] activarTags(Box[] __) {
    String x = Casting.toString(__[0]);
    String pos = Casting.toString(__[1]);
    /*#shared attributes:
    $TagStr{"tag"} = 0;*/
    TagStr.put("lemma", 0);
    TagStr.put("token", 0);
    if (Regex.match(x, "^PRO", "", false)) {
      TagStr.put("type", 0);
      TagStr.put("person", 0);
      TagStr.put("gender", 0);
      TagStr.put("number", 0);
      TagStr.put("case", 0);
      TagStr.put("possessor", 0);
      TagStr.put("politeness", 0);
      TagStr.put("pos", Casting.toInteger(pos));
      return new Box[] {Casting.box(1)};
    }
    /*#conjunctions:*/
    else if (Regex.match(x, "^C", "", false)) {
      TagStr.put("type", 0);
      TagStr.put("pos", Casting.toInteger(pos));
      return new Box[] {Casting.box(1)};
    }
    /*#interjections:*/
    else if (Regex.match(x, "^I", "", false)) {
      TagStr.put("type", 0);
      TagStr.put("pos", Casting.toInteger(pos));
      return new Box[] {Casting.box(1)};
    }
    /*#numbers*/
    else if (Regex.match(x, "^CARD", "", false)) {
      TagStr.put("number", Casting.toInteger("P"));
      TagStr.put("person", 0);
      TagStr.put("gender", 0);
      TagStr.put("pos", Casting.toInteger(pos));
      return new Box[] {Casting.box(1)};
    } else if (Regex.match(x, "^ADJ", "", false)) {
      TagStr.put("type", 0);
      TagStr.put("degree", 0);
      TagStr.put("gender", 0);
      TagStr.put("number", 0);
      TagStr.put("function", 0);
      TagStr.put("pos", Casting.toInteger(pos));
      return new Box[] {Casting.box(1)};
    } else if (Regex.match(x, "^ADV", "", false)) {
      TagStr.put("type", 0);
      TagStr.put("pos", Casting.toInteger(pos));
      return new Box[] {Casting.box(1)};
    } else if (Regex.match(x, "^PRP", "", false)) {
      TagStr.put("type", 0);
      TagStr.put("pos", Casting.toInteger(pos));
      return new Box[] {Casting.box(1)};
    } else if (Regex.match(x, "^NOUN", "", false)) {
      TagStr.put("type", 0);
      TagStr.put("gender", 0);
      TagStr.put("number", 0);
      TagStr.put("person", 0);
      TagStr.put("pos", Casting.toInteger(pos));
      return new Box[] {Casting.box(1)};
    } else if (Regex.match(x, "^DT", "", false)) {
      TagStr.put("type", 0);
      TagStr.put("person", 0);
      TagStr.put("gender", 0);
      TagStr.put("number", 0);
      TagStr.put("possessor", 0);
      TagStr.put("pos", Casting.toInteger(pos));
      return new Box[] {Casting.box(1)};
    }
    /*#mudar tags nos verbos:*/
    else if (Regex.match(x, "^VERB", "", false)) {
      TagStr.put("type", 0);
      TagStr.put("mode", 0);
      TagStr.put("tense", 0);
      TagStr.put("person", 0);
      TagStr.put("number", 0);
      TagStr.put("gender", 0);
      TagStr.put("pos", Casting.toInteger(pos));
      return new Box[] {Casting.box(1)};
    } else {
      return new Box[] {Casting.box(0)};
    }
  }

  public static Box[] desactivarTags(Box[] __) {
    String x = Casting.toString(__[0]);
    Perl.delete(TagStr, "type");
    Perl.delete(TagStr, "person");
    Perl.delete(TagStr, "gender");
    Perl.delete(TagStr, "number");
    Perl.delete(TagStr, "case");
    Perl.delete(TagStr, "possessor");
    Perl.delete(TagStr, "politeness");
    Perl.delete(TagStr, "mode");
    Perl.delete(TagStr, "tense");
    Perl.delete(TagStr, "function");
    Perl.delete(TagStr, "degree");
    Perl.delete(TagStr, "pos");
    Perl.delete(TagStr, "lemma");
    Perl.delete(TagStr, "token");
    /* delete $TagStr{"tag"} ;*/
    return new Box[] {Casting.box(1)};
  }

  public static Box[] ConvertChar(Box[] __) {
    String x = Casting.toString(__[0]);
    String y = Casting.toString(__[1]);
    info = Regex.substitution(info, "lemma:" + x, "lemma:\\*" + y + "\\*", "", true);
    info = Regex.substitution(info, "token:" + x, "token:\\*" + y + "\\*", "", true);
    return new Box[0];
  }

  private static void global() {
    Ref<String> pd_1;
    flag = Pd.rn(Pd.ARGV = Perl.shift(Pd.ARGV, pd_1 = new Ref<>()), pd_1);
    /*# -a por defeito*/
    if (!Perl.defined(flag)) {
      flag = "-a";
    }
    /*fronteira de frase:*/
    Border = "SENT";
    /*identificar nomes de dependencias lexicais (idiomaticas)*/
    DepLex = "^<$|^>$|lex$";
    /*string com todos os atributos:*/
    b2 = "[^ _<>]*";
    a2 = "_<" + b2 + ">"; /*#todos os atributos*/
    l = "_<" + b2; /*#atributos parte esquerda*/
    r = b2 + ">"; /*#atributos parte direita
##########################GENERATED CODE BY COMPI#############################################
####################################### POS TAGS ########################################*/
    ADJ = "ADJ_[0-9]+";
    NOUN = "NOUN_[0-9]+";
    PRP = "PRP_[0-9]+";
    ADV = "ADV_[0-9]+";
    CARD = "CARD_[0-9]+";
    CONJ = "CONJ_[0-9]+";
    DET = "DT_[0-9]+";
    PRO = "PRO_[0-9]+";
    VERB = "VERB_[0-9]+";
    I = "I_[0-9]+";
    DATE = "DATE_[0-9]+";
    POS = "POS_[0-9]+";
    PCLE = "PCLE_[0-9]+";
    EX = "EX_[0-9]+";
    Fc = "Fc_[0-9]+";
    Fg = "Fg_[0-9]+";
    Fz = "Fz_[0-9]+";
    Fe = "Fe_[0-9]+";
    Fd = "Fd_[0-9]+";
    Fx = "Fx_[0-9]+";
    Fpa = "Fpa_[0-9]+";
    Fpt = "Fpt_[0-9]+";
    SENT = "SENT_[0-9]+";
    NOMINAL = "NOUN_[0-9]+" + a2 + "|CONJ_[0-9]+" + l + "coord:noun" + r;
    NOUNCOORD =
        "CARD_[0-9]+"
            + a2
            + "|DATE_[0-9]+"
            + a2
            + "|NOUN_[0-9]+"
            + a2
            + "|CONJ_[0-9]+"
            + l
            + "coord:noun"
            + r;
    NOUNSINGLE = "CARD_[0-9]+" + a2 + "|DATE_[0-9]+" + a2 + "|NOUN_[0-9]+";
    NPCOORD = "CONJ_[0-9]+" + l + "coord:np" + r + "|NOUN_[0-9]+" + l + "type:P" + r;
    X = "[A-Z]+_[0-9]+";
    NOTVERB = "[^V][^E]+_[0-9]+";
    PUNCT = "F[a-z]+_[0-9]+";
    /*################################### LEXICAL CLASSES #####################################*/
    Quant =
        "(?:very|more|less|least|most|quite|as|muy|mucho|bastante|bien|casi|tan|muy|bem|ben|menos|poco|mui|moi|muito|tão|más|mais|máis|pouco|peu|assez|plus|moins|aussi|)";
    Partitive = "(?:de|of|)";
    PrepLocs = "(?:a|de|por|par|by|to|)";
    PrepRA = "(?:de|com|con|sobre|sem|sen|entre|of|with|about|without|between|on|avec|sûr|)";
    PrepMA =
        "(?:hasta|até|hacia|desde|em|en|para|since|until|at|in|for|to|jusqu\'|depuis|pour|dans|)";
    cliticopers =
        "(?:lo|la|los|las|le|les|nos|os|me|te|se|Lo|La|Las|Le|Les|Nos|Os|Me|Te|Se|lle|lles|lhe|lhes|Lles|Lhes|Lle|Lhe|che|ches|Che|Ches|o|O|a|A|him|her|me|us|you|them|lui|leur|leurs|)";
    PROperssuj =
        "(?:yo|tú|usted|él|ella|nosotros|vosotros|ellos|ellas|ustedes|eu|ti|tu|vostede|você|ele|ela|nós|vós|eles|elas|vostedes|vocês|eles|elas|you|i|we|they|he|she|je|il|elle|ils|elles|nous|vous|)";
    PROsujgz = "(?:eu|ti|tu|vostede|você|el|ele|ela|nós|vós|eles|elas|vostedes|vocês|eles|elas|)";
    VModalEN = "(?:can|cannot|should|must|shall|will|would|may|might|)";
    Vpass = "(?:ser|be|être|)";
    Vaux = "(?:haber|haver|ter|have|avoir|)";
    NincSp =
        "(?:alusión|comentario|referencia|llamamiento|mención|observación|declaración|propuesta|pregunta|)";
    NincExp =
        "(?:afecto|alegría|amparo|angustia|ánimo|cariño|cobardía|comprensión|consuelo|corte|daño|disgusto|duda|escándalo|fobia|gana|gracias|gusto|inquietud|)";
    PTa =
        "(?:aceder|acostumar|acudir|adaptar|comprometer|chegar|cheirar|dar|dedicar|equivaler|ir|olhar|negar|pertencer|recorrer|referir|regressar|renunciar|subir|sustrair|unir|vincular|voltar|acostumbrar|llegar|oler|mirar|pertenecer|recurrir|sustraer|volver|)";
    /*###################################END CODE BY COMPI################################################*/
    i = 0;
    listTags = Casting.toInteger("");
    seq = Casting.toInteger("");
    CountLines = 0;
    info = "";
    Token = new PerlList<String>();
    Tag = new PerlList<String>();
    Lemma = new PerlList<String>();
    Attributes = new PerlList<String>();
    ATTR = new PerlList<PerlMap<String>>();
    ATTR_lemma = new PerlMap<String>();
    TagStr = new PerlMap<Integer>();
    IDF = new PerlMap<String>();
    Ordenar = new PerlMap<Integer>();
    j = 0;
    String line;
    while (Casting.toBoolean(line = Pd.read())) {
      line = Perl.chop(line);
      String token;
      String[] pd_2;
      pd_2 = Perl.split(" ", line);
      token = pd_2[0];
      info = pd_2[1];
      if ((Pd.mod(Pd.checkNull(CountLines), 100)) == 0) {
        Perl.printf(PerlFile.STDERR, "- - - processar linha:(%6d) - - -\r", CountLines);
      }
      CountLines = Pd.checkNull(CountLines) + 1;
      /*##Convertimos caracteres significativos na sintaxe de DepPattern em tags especiais*/
      if (Regex.match(token, ":", "", false)) {
        ConvertChar(new Box[] {Casting.box("\\:"), Casting.box("Fd")});
      }
      if (Regex.match(token, "\\|", "", false)) {
        ConvertChar(new Box[] {Casting.box("\\|"), Casting.box("Fz")});
      }
      if (Regex.match(token, ">", "", false)) {
        ConvertChar(new Box[] {Casting.box("\\>"), Casting.box("Fz1")});
      }
      if (Regex.match(token, "<", "", false)) {
        ConvertChar(new Box[] {Casting.box("\\<"), Casting.box("Fz2")});
      }
      PerlMap<String> Exp = new PerlMap<String>();
      /*#organizamos a informacao de cada token:*/
      if (Pd.compare(Pd.checkNull(info), "") != 0) {
        String[] pd_1info = Perl.split("\\|", info);
        for (i = 0;
            Pd.checkNull(i) <= Pd.checkNull((pd_1info.length - 1));
            i = Pd.checkNull(i) + 1) {
          if (Pd.compare(Pd.checkNull(pd_1info[i]), "") != 0) {
            String[] pd_3;
            pd_3 = Perl.split(":", pd_1info[i]);
            String attrib = pd_3[0];
            String value = pd_3[1];
            Exp.put(attrib, value);
          }
        }
      }
      /*#construimos os vectores da oracao*/
      if (Pd.compare(Pd.checkNull(Exp.get("tag")), Pd.checkNull(Border)) != 0) {
        Token.set(j, token);
        if (Pd.compare(Pd.checkNull(info), "") != 0) {
          Lemma.set(j, Exp.get("lemma"));
          Tag.set(j, Exp.get("tag"));
          Attributes.set(j, "");
          for (String at : Perl.sort(Perl.keys(Exp))) {
            if (Pd.compare(Pd.checkNull(at), "tag") != 0) {
              Attributes.set(j, Pd.checkNull(Attributes.get(j)) + at + ":" + Exp.get(at) + "|");
              ATTR.get(j).put(at, Exp.get(at));
            }
          }
        }
        j = Pd.checkNull(j) + 1;
        /*print STDERR "$j\r";*/
      } else if (Pd.compare(Pd.checkNull(Exp.get("tag")), Pd.checkNull(Border)) == 0) {
        Token.set(j, token);
        Lemma.set(j, Exp.get("lemma"));
        Tag.set(j, Exp.get("tag"));
        Attributes.set(j, "");
        for (String at : Perl.sort(Perl.keys(Exp))) {
          if (Pd.compare(Pd.checkNull(at), "tag") != 0) {
            Attributes.set(j, Pd.checkNull(Attributes.get(j)) + at + ":" + Exp.get(at) + "|");
            ATTR.get(j).put(at, Exp.get(at));
          }
        }
        /*#construimos os strings com a lista de tags-atributos e os token-tags da oraçao*/
        for (i = 0; Pd.checkNull(i) <= Pd.checkNull((Token.size() - 1)); i = Pd.checkNull(i) + 1) {
          ReConvertChar(new Box[] {Casting.box("\\:"), Casting.box("Fd"), Casting.box(i)});
          ReConvertChar(new Box[] {Casting.box("\\|"), Casting.box("Fz"), Casting.box(i)});
          ReConvertChar(new Box[] {Casting.box("\\>"), Casting.box("Fz1"), Casting.box(i)});
          ReConvertChar(new Box[] {Casting.box("\\<"), Casting.box("Fz2"), Casting.box(i)});
          listTags =
              Casting.toInteger(
                  Pd.checkNull(Casting.toString(listTags))
                      + Tag.get(i)
                      + "_"
                      + i
                      + "_<"
                      + Attributes.get(i)
                      + ">");
          seq =
              Casting.toInteger(
                  Pd.checkNull(Casting.toString(seq))
                      + Token.get(i)
                      + "_"
                      + Tag.get(i)
                      + "_"
                      + i
                      + "_<"
                      + Attributes.get(i)
                      + ">"
                      + " ");
        } /*#fim do for
          $seq .= "\." . "_" . $Border ;*/
        Boolean STOP = false;
        /*$iteration=0;*/
        while (!STOP) {
          String ListInit = Casting.toString(listTags);
          /*$iteration++;
          print STDERR "$iteration\n";*/
          String[] temp = null;
          String Rel = "";
          /*##########################BEGIN GRAMMAR#############################################
          Single: PRP<lemma:so|because|though|if> [X]?
          Corr: tag:CONJ, type:S*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(" + PRP + l + "lemma:(?:so|because|though|if)\\|" + r + ")(?:" + X + a2 + ")?",
                  "",
                  true);
          Rel = "Single";
          Head(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + PRP
                          + l
                          + "lemma:(?:so|because|though|if)\\|"
                          + r
                          + ")("
                          + X
                          + a2
                          + ")?",
                      "$1$2",
                      "",
                      true));
          Corr(
              Pd.union()
                  .append(new Box[] {Casting.box("Head"), Casting.box("tag:CONJ,type:S")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          /* Single: [NOUN] [Fc]? CONJ<type:S> [NOUN|PRO<type:D|P|I|X>] [VERB]
          Corr: tag:PRO, type:W*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")?("
                      + CONJ
                      + l
                      + "type:S\\|"
                      + r
                      + ")(?:"
                      + NOUN
                      + a2
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")(?:"
                      + VERB
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "Single";
          Head(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + NOUN
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")?("
                          + CONJ
                          + l
                          + "type:S\\|"
                          + r
                          + ")("
                          + NOUN
                          + a2
                          + "|"
                          + PRO
                          + l
                          + "type:(?:D|P|I|X)\\|"
                          + r
                          + ")("
                          + VERB
                          + a2
                          + ")",
                      "$1$2$3$4$5",
                      "",
                      true));
          Corr(
              Pd.union()
                  .append(new Box[] {Casting.box("Head"), Casting.box("tag:PRO,type:W")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          /* Single: [PRO<lemma:it|there>] [VERB<lemma:be>] [NOUN] PRO<lemma:that>
          Corr: tag:CONJ, type:S*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + PRO
                      + l
                      + "lemma:(?:it|there)\\|"
                      + r
                      + ")(?:"
                      + VERB
                      + l
                      + "lemma:be\\|"
                      + r
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")("
                      + PRO
                      + l
                      + "lemma:that\\|"
                      + r
                      + ")",
                  "",
                  true);
          Rel = "Single";
          Head(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + PRO
                          + l
                          + "lemma:(?:it|there)\\|"
                          + r
                          + ")("
                          + VERB
                          + l
                          + "lemma:be\\|"
                          + r
                          + ")("
                          + NOUN
                          + a2
                          + ")("
                          + PRO
                          + l
                          + "lemma:that\\|"
                          + r
                          + ")",
                      "$1$2$3$4",
                      "",
                      true));
          Corr(
              Pd.union()
                  .append(new Box[] {Casting.box("Head"), Casting.box("tag:CONJ,type:S")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          /* PunctR: X Fz|Fe*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(" + X + a2 + ")(" + Fz + a2 + "|" + Fe + a2 + ")",
                  "",
                  true);
          Rel = "PunctR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + X + a2 + ")(" + Fz + a2 + "|" + Fe + a2 + ")",
                      "$1",
                      "",
                      true));
          /* PunctL: Fz|Fe X*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(" + Fz + a2 + "|" + Fe + a2 + ")(" + X + a2 + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + Fz + a2 + "|" + Fe + a2 + ")(" + X + a2 + ")",
                      "$2",
                      "",
                      true));
          /* >: VERB<lemma:take|have> NOUN<number:S> [PRP]*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + VERB
                      + l
                      + "lemma:(?:take|have)\\|"
                      + r
                      + ")("
                      + NOUN
                      + l
                      + "number:S\\|"
                      + r
                      + ")(?:"
                      + PRP
                      + a2
                      + ")",
                  "",
                  true);
          Rel = ">";
          HeadDep_lex(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + VERB
                          + l
                          + "lemma:(?:take|have)\\|"
                          + r
                          + ")("
                          + NOUN
                          + l
                          + "number:S\\|"
                          + r
                          + ")("
                          + PRP
                          + a2
                          + ")",
                      "$1$3",
                      "",
                      true));
          LEX(new Box[0]);
          /* >: VERB<lemma:be> ADJ [PRP]*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(" + VERB + l + "lemma:be\\|" + r + ")(" + ADJ + a2 + ")(?:" + PRP + a2 + ")",
                  "",
                  true);
          Rel = ">";
          HeadDep_lex(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + VERB + l + "lemma:be\\|" + r + ")(" + ADJ + a2 + ")(" + PRP + a2 + ")",
                      "$1$3",
                      "",
                      true));
          LEX(new Box[0]);
          /* <: [VERB<lemma:be|become>] ADV<lemma:$Quant> ADJ [PRP<lemma:than|as>]
          NEXT
          >: VERB<lemma:be|become> [ADV<lemma:$Quant>] ADJ [PRP<lemma:than|as>]*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + VERB
                      + l
                      + "lemma:(?:be|become)\\|"
                      + r
                      + ")("
                      + ADV
                      + l
                      + "lemma:"
                      + Quant
                      + "\\|"
                      + r
                      + ")("
                      + ADJ
                      + a2
                      + ")(?:"
                      + PRP
                      + l
                      + "lemma:(?:than|as)\\|"
                      + r
                      + ")",
                  "",
                  true);
          Rel = "<";
          DepHead_lex(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + VERB
                      + l
                      + "lemma:(?:be|become)\\|"
                      + r
                      + ")(?:"
                      + ADV
                      + l
                      + "lemma:"
                      + Quant
                      + "\\|"
                      + r
                      + ")("
                      + ADJ
                      + a2
                      + ")(?:"
                      + PRP
                      + l
                      + "lemma:(?:than|as)\\|"
                      + r
                      + ")",
                  "",
                  true);
          Rel = ">";
          HeadDep_lex(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + VERB
                          + l
                          + "lemma:(?:be|become)\\|"
                          + r
                          + ")("
                          + ADV
                          + l
                          + "lemma:"
                          + Quant
                          + "\\|"
                          + r
                          + ")("
                          + ADJ
                          + a2
                          + ")("
                          + PRP
                          + l
                          + "lemma:(?:than|as)\\|"
                          + r
                          + ")",
                      "$1$4",
                      "",
                      true));
          LEX(new Box[0]);
          /* >: VERB<lemma:be|become>  ADJ [PRP<lemma:than|as>]*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + VERB
                      + l
                      + "lemma:(?:be|become)\\|"
                      + r
                      + ")("
                      + ADJ
                      + a2
                      + ")(?:"
                      + PRP
                      + l
                      + "lemma:(?:than|as)\\|"
                      + r
                      + ")",
                  "",
                  true);
          Rel = ">";
          HeadDep_lex(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + VERB
                          + l
                          + "lemma:(?:be|become)\\|"
                          + r
                          + ")("
                          + ADJ
                          + a2
                          + ")("
                          + PRP
                          + l
                          + "lemma:(?:than|as)\\|"
                          + r
                          + ")",
                      "$1$3",
                      "",
                      true));
          LEX(new Box[0]);
          /* <: PRP<lemma:in> X<lemma:order> [PRP<lemma:to>]
          NEXT
          <:  [PRP<lemma:in>] X<lemma:order> PRP<lemma:to>*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + PRP
                      + l
                      + "lemma:in\\|"
                      + r
                      + ")("
                      + X
                      + l
                      + "lemma:order\\|"
                      + r
                      + ")(?:"
                      + PRP
                      + l
                      + "lemma:to\\|"
                      + r
                      + ")",
                  "",
                  true);
          Rel = "<";
          DepHead_lex(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + PRP
                      + l
                      + "lemma:in\\|"
                      + r
                      + ")("
                      + X
                      + l
                      + "lemma:order\\|"
                      + r
                      + ")("
                      + PRP
                      + l
                      + "lemma:to\\|"
                      + r
                      + ")",
                  "",
                  true);
          Rel = "<";
          DepHead_lex(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + PRP
                          + l
                          + "lemma:in\\|"
                          + r
                          + ")("
                          + X
                          + l
                          + "lemma:order\\|"
                          + r
                          + ")("
                          + PRP
                          + l
                          + "lemma:to\\|"
                          + r
                          + ")",
                      "$3",
                      "",
                      true));
          LEX(new Box[0]);
          /* AdjnL: ADV<lemma:$Quant> ADV|ADJ*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(" + ADV + l + "lemma:" + Quant + "\\|" + r + ")(" + ADV + a2 + "|" + ADJ + a2
                      + ")",
                  "",
                  true);
          Rel = "AdjnL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + ADV + l + "lemma:" + Quant + "\\|" + r + ")(" + ADV + a2 + "|" + ADJ
                          + a2 + ")",
                      "$2",
                      "",
                      true));
          /* PunctL: [ADJ] Fc ADJ [NOUN]
          NEXT
          AdjnL: ADJ [Fc] ADJ [NOUN]
          Recursivity: 5*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:" + ADJ + a2 + ")(" + Fc + a2 + ")(" + ADJ + a2 + ")(?:" + NOUN + a2 + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(" + ADJ + a2 + ")(?:" + Fc + a2 + ")(" + ADJ + a2 + ")(?:" + NOUN + a2 + ")",
                  "",
                  true);
          Rel = "AdjnL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + ADJ + a2 + ")(" + Fc + a2 + ")(" + ADJ + a2 + ")(" + NOUN + a2 + ")",
                      "$3$4",
                      "",
                      true));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:" + ADJ + a2 + ")(" + Fc + a2 + ")(" + ADJ + a2 + ")(?:" + NOUN + a2 + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(" + ADJ + a2 + ")(?:" + Fc + a2 + ")(" + ADJ + a2 + ")(?:" + NOUN + a2 + ")",
                  "",
                  true);
          Rel = "AdjnL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + ADJ + a2 + ")(" + Fc + a2 + ")(" + ADJ + a2 + ")(" + NOUN + a2 + ")",
                      "$3$4",
                      "",
                      true));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:" + ADJ + a2 + ")(" + Fc + a2 + ")(" + ADJ + a2 + ")(?:" + NOUN + a2 + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(" + ADJ + a2 + ")(?:" + Fc + a2 + ")(" + ADJ + a2 + ")(?:" + NOUN + a2 + ")",
                  "",
                  true);
          Rel = "AdjnL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + ADJ + a2 + ")(" + Fc + a2 + ")(" + ADJ + a2 + ")(" + NOUN + a2 + ")",
                      "$3$4",
                      "",
                      true));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:" + ADJ + a2 + ")(" + Fc + a2 + ")(" + ADJ + a2 + ")(?:" + NOUN + a2 + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(" + ADJ + a2 + ")(?:" + Fc + a2 + ")(" + ADJ + a2 + ")(?:" + NOUN + a2 + ")",
                  "",
                  true);
          Rel = "AdjnL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + ADJ + a2 + ")(" + Fc + a2 + ")(" + ADJ + a2 + ")(" + NOUN + a2 + ")",
                      "$3$4",
                      "",
                      true));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:" + ADJ + a2 + ")(" + Fc + a2 + ")(" + ADJ + a2 + ")(?:" + NOUN + a2 + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(" + ADJ + a2 + ")(?:" + Fc + a2 + ")(" + ADJ + a2 + ")(?:" + NOUN + a2 + ")",
                  "",
                  true);
          Rel = "AdjnL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + ADJ + a2 + ")(" + Fc + a2 + ")(" + ADJ + a2 + ")(" + NOUN + a2 + ")",
                      "$3$4",
                      "",
                      true));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:" + ADJ + a2 + ")(" + Fc + a2 + ")(" + ADJ + a2 + ")(?:" + NOUN + a2 + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(" + ADJ + a2 + ")(?:" + Fc + a2 + ")(" + ADJ + a2 + ")(?:" + NOUN + a2 + ")",
                  "",
                  true);
          Rel = "AdjnL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + ADJ + a2 + ")(" + Fc + a2 + ")(" + ADJ + a2 + ")(" + NOUN + a2 + ")",
                      "$3$4",
                      "",
                      true));
          /* CoordL: ADJ CONJ<(type:C)|(lemma:and|or|y|e|et|o|ou)> [ADJ]
          NEXT
          CoordR: [ADJ]  CONJ<(type:C)|(lemma:and|or|y|e|et|o|ou)> ADJ
          Add: coord:adj
          Inherit: gender, number*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + ADJ
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + ADJ
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + ADJ
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")("
                      + ADJ
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + ADJ
                          + a2
                          + ")("
                          + CONJ
                          + l
                          + "type:C\\|"
                          + r
                          + "|"
                          + CONJ
                          + l
                          + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                          + r
                          + ")("
                          + ADJ
                          + a2
                          + ")",
                      "$2",
                      "",
                      true));
          Inherit(
              Pd.union()
                  .append(new Box[] {Casting.box("HeadDep"), Casting.box("gender,number")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          Add(
              Pd.union()
                  .append(new Box[] {Casting.box("HeadDep"), Casting.box("coord:adj")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          /* CoordL: ADJ [Fc] [ADJ] [Fc] CONJ<(type:C)|(lemma:and|or|y|e|et|o|ou)> [ADJ]
          NEXT
          PunctL:  [ADJ] Fc [ADJ] [Fc] CONJ<(type:C)|(lemma:and|or|y|e|et|o|ou)> [ADJ]
          Recursivity: 10*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + ADJ
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")(?:"
                      + ADJ
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + ADJ
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + ADJ
                      + a2
                      + ")("
                      + Fc
                      + a2
                      + ")(?:"
                      + ADJ
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + ADJ
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + ADJ
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + ADJ
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + CONJ
                          + l
                          + "type:C\\|"
                          + r
                          + "|"
                          + CONJ
                          + l
                          + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                          + r
                          + ")("
                          + ADJ
                          + a2
                          + ")",
                      "$3$4$5$6",
                      "",
                      true));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + ADJ
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")(?:"
                      + ADJ
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + ADJ
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + ADJ
                      + a2
                      + ")("
                      + Fc
                      + a2
                      + ")(?:"
                      + ADJ
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + ADJ
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + ADJ
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + ADJ
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + CONJ
                          + l
                          + "type:C\\|"
                          + r
                          + "|"
                          + CONJ
                          + l
                          + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                          + r
                          + ")("
                          + ADJ
                          + a2
                          + ")",
                      "$3$4$5$6",
                      "",
                      true));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + ADJ
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")(?:"
                      + ADJ
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + ADJ
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + ADJ
                      + a2
                      + ")("
                      + Fc
                      + a2
                      + ")(?:"
                      + ADJ
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + ADJ
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + ADJ
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + ADJ
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + CONJ
                          + l
                          + "type:C\\|"
                          + r
                          + "|"
                          + CONJ
                          + l
                          + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                          + r
                          + ")("
                          + ADJ
                          + a2
                          + ")",
                      "$3$4$5$6",
                      "",
                      true));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + ADJ
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")(?:"
                      + ADJ
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + ADJ
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + ADJ
                      + a2
                      + ")("
                      + Fc
                      + a2
                      + ")(?:"
                      + ADJ
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + ADJ
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + ADJ
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + ADJ
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + CONJ
                          + l
                          + "type:C\\|"
                          + r
                          + "|"
                          + CONJ
                          + l
                          + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                          + r
                          + ")("
                          + ADJ
                          + a2
                          + ")",
                      "$3$4$5$6",
                      "",
                      true));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + ADJ
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")(?:"
                      + ADJ
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + ADJ
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + ADJ
                      + a2
                      + ")("
                      + Fc
                      + a2
                      + ")(?:"
                      + ADJ
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + ADJ
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + ADJ
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + ADJ
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + CONJ
                          + l
                          + "type:C\\|"
                          + r
                          + "|"
                          + CONJ
                          + l
                          + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                          + r
                          + ")("
                          + ADJ
                          + a2
                          + ")",
                      "$3$4$5$6",
                      "",
                      true));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + ADJ
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")(?:"
                      + ADJ
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + ADJ
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + ADJ
                      + a2
                      + ")("
                      + Fc
                      + a2
                      + ")(?:"
                      + ADJ
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + ADJ
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + ADJ
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + ADJ
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + CONJ
                          + l
                          + "type:C\\|"
                          + r
                          + "|"
                          + CONJ
                          + l
                          + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                          + r
                          + ")("
                          + ADJ
                          + a2
                          + ")",
                      "$3$4$5$6",
                      "",
                      true));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + ADJ
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")(?:"
                      + ADJ
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + ADJ
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + ADJ
                      + a2
                      + ")("
                      + Fc
                      + a2
                      + ")(?:"
                      + ADJ
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + ADJ
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + ADJ
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + ADJ
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + CONJ
                          + l
                          + "type:C\\|"
                          + r
                          + "|"
                          + CONJ
                          + l
                          + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                          + r
                          + ")("
                          + ADJ
                          + a2
                          + ")",
                      "$3$4$5$6",
                      "",
                      true));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + ADJ
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")(?:"
                      + ADJ
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + ADJ
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + ADJ
                      + a2
                      + ")("
                      + Fc
                      + a2
                      + ")(?:"
                      + ADJ
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + ADJ
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + ADJ
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + ADJ
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + CONJ
                          + l
                          + "type:C\\|"
                          + r
                          + "|"
                          + CONJ
                          + l
                          + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                          + r
                          + ")("
                          + ADJ
                          + a2
                          + ")",
                      "$3$4$5$6",
                      "",
                      true));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + ADJ
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")(?:"
                      + ADJ
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + ADJ
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + ADJ
                      + a2
                      + ")("
                      + Fc
                      + a2
                      + ")(?:"
                      + ADJ
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + ADJ
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + ADJ
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + ADJ
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + CONJ
                          + l
                          + "type:C\\|"
                          + r
                          + "|"
                          + CONJ
                          + l
                          + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                          + r
                          + ")("
                          + ADJ
                          + a2
                          + ")",
                      "$3$4$5$6",
                      "",
                      true));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + ADJ
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")(?:"
                      + ADJ
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + ADJ
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + ADJ
                      + a2
                      + ")("
                      + Fc
                      + a2
                      + ")(?:"
                      + ADJ
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + ADJ
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + ADJ
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + ADJ
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + CONJ
                          + l
                          + "type:C\\|"
                          + r
                          + "|"
                          + CONJ
                          + l
                          + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                          + r
                          + ")("
                          + ADJ
                          + a2
                          + ")",
                      "$3$4$5$6",
                      "",
                      true));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + ADJ
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")(?:"
                      + ADJ
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + ADJ
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + ADJ
                      + a2
                      + ")("
                      + Fc
                      + a2
                      + ")(?:"
                      + ADJ
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + ADJ
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + ADJ
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + ADJ
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + CONJ
                          + l
                          + "type:C\\|"
                          + r
                          + "|"
                          + CONJ
                          + l
                          + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                          + r
                          + ")("
                          + ADJ
                          + a2
                          + ")",
                      "$3$4$5$6",
                      "",
                      true));
          /* PunctL: [ADJ] Fc CONJ<(type:C)|(lemma:and|or|y|e|et|o|ou)> [ADJ]
          NEXT
          CoordL: ADJ [Fc] CONJ<(type:C)|(lemma:and|or|y|e|et|o|ou)> [ADJ]
          NEXT
          CoordR:  [ADJ] [Fc] CONJ<(type:C)|(lemma:and|or|y|e|et|o|ou)> ADJ
          Add: coord:adj
          Inherit: gender, number*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + ADJ
                      + a2
                      + ")("
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + ADJ
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + ADJ
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + ADJ
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + ADJ
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")("
                      + ADJ
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + ADJ
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + CONJ
                          + l
                          + "type:C\\|"
                          + r
                          + "|"
                          + CONJ
                          + l
                          + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                          + r
                          + ")("
                          + ADJ
                          + a2
                          + ")",
                      "$3",
                      "",
                      true));
          Inherit(
              Pd.union()
                  .append(new Box[] {Casting.box("HeadDep"), Casting.box("gender,number")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          Add(
              Pd.union()
                  .append(new Box[] {Casting.box("HeadDep"), Casting.box("coord:adj")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          /* CoordL: ADJ  CONJ<(type:C)|(lemma:and|or|y|e|et|o|ou)> [NOUN] [NOUN]
          NEXT
          CoordR: [ADJ]  CONJ<(type:C)|(lemma:and|or|y|e|et|o|ou)> NOUN [NOUN]
          Add: coord:adj*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + ADJ
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + ADJ
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")("
                      + NOUN
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + ADJ
                          + a2
                          + ")("
                          + CONJ
                          + l
                          + "type:C\\|"
                          + r
                          + "|"
                          + CONJ
                          + l
                          + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                          + r
                          + ")("
                          + NOUN
                          + a2
                          + ")("
                          + NOUN
                          + a2
                          + ")",
                      "$2$4",
                      "",
                      true));
          Add(
              Pd.union()
                  .append(new Box[] {Casting.box("HeadDep"), Casting.box("coord:adj")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          /* CoordL: ADJ [Fc] [ADJ] [Fc] CONJ<(type:C)|(lemma:and|or|y|e|et|o|ou)> [NOUN] [NOUN]
          NEXT
          PunctL:  [ADJ] Fc [ADJ] [Fc] CONJ<(type:C)|(lemma:and|or|y|e|et|o|ou)> [NOUN] [NOUN]
          Recursivity: 3*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + ADJ
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")(?:"
                      + ADJ
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + ADJ
                      + a2
                      + ")("
                      + Fc
                      + a2
                      + ")(?:"
                      + ADJ
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + ADJ
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + ADJ
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + CONJ
                          + l
                          + "type:C\\|"
                          + r
                          + "|"
                          + CONJ
                          + l
                          + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                          + r
                          + ")("
                          + NOUN
                          + a2
                          + ")("
                          + NOUN
                          + a2
                          + ")",
                      "$3$4$5$6$7",
                      "",
                      true));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + ADJ
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")(?:"
                      + ADJ
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + ADJ
                      + a2
                      + ")("
                      + Fc
                      + a2
                      + ")(?:"
                      + ADJ
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + ADJ
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + ADJ
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + CONJ
                          + l
                          + "type:C\\|"
                          + r
                          + "|"
                          + CONJ
                          + l
                          + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                          + r
                          + ")("
                          + NOUN
                          + a2
                          + ")("
                          + NOUN
                          + a2
                          + ")",
                      "$3$4$5$6$7",
                      "",
                      true));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + ADJ
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")(?:"
                      + ADJ
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + ADJ
                      + a2
                      + ")("
                      + Fc
                      + a2
                      + ")(?:"
                      + ADJ
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + ADJ
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + ADJ
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + CONJ
                          + l
                          + "type:C\\|"
                          + r
                          + "|"
                          + CONJ
                          + l
                          + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                          + r
                          + ")("
                          + NOUN
                          + a2
                          + ")("
                          + NOUN
                          + a2
                          + ")",
                      "$3$4$5$6$7",
                      "",
                      true));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + ADJ
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")(?:"
                      + ADJ
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + ADJ
                      + a2
                      + ")("
                      + Fc
                      + a2
                      + ")(?:"
                      + ADJ
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + ADJ
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + ADJ
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + CONJ
                          + l
                          + "type:C\\|"
                          + r
                          + "|"
                          + CONJ
                          + l
                          + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                          + r
                          + ")("
                          + NOUN
                          + a2
                          + ")("
                          + NOUN
                          + a2
                          + ")",
                      "$3$4$5$6$7",
                      "",
                      true));
          /* CoordL: ADJ [Fc] CONJ<(type:C)|(lemma:and|or|y|e|et|o|ou)> [NOUN] [NOUN]
          NEXT
          PunctL: [ADJ] Fc CONJ<(type:C)|(lemma:and|or|y|e|et|o|ou)> [NOUN] [NOUN]
          NEXT
          CoordR:  [ADJ] [Fc] CONJ<(type:C)|(lemma:and|or|y|e|et|o|ou)> NOUN [NOUN]
          Add: coord:adj
          Inherit: gender, number*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + ADJ
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + ADJ
                      + a2
                      + ")("
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + ADJ
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")("
                      + NOUN
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + ADJ
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + CONJ
                          + l
                          + "type:C\\|"
                          + r
                          + "|"
                          + CONJ
                          + l
                          + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                          + r
                          + ")("
                          + NOUN
                          + a2
                          + ")("
                          + NOUN
                          + a2
                          + ")",
                      "$3$5",
                      "",
                      true));
          Inherit(
              Pd.union()
                  .append(new Box[] {Casting.box("HeadDep"), Casting.box("gender,number")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          Add(
              Pd.union()
                  .append(new Box[] {Casting.box("HeadDep"), Casting.box("coord:adj")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          /* AdjnL: NOUN NOUN
          Recursivity: 1*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags), "(" + NOUN + a2 + ")(" + NOUN + a2 + ")", "", true);
          Rel = "AdjnL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + NOUN + a2 + ")(" + NOUN + a2 + ")",
                      "$2",
                      "",
                      true));
          temp =
              Regex.matcher(
                  Casting.toString(listTags), "(" + NOUN + a2 + ")(" + NOUN + a2 + ")", "", true);
          Rel = "AdjnL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + NOUN + a2 + ")(" + NOUN + a2 + ")",
                      "$2",
                      "",
                      true));
          /* CprepL: NOUN POS NOUN*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(" + NOUN + a2 + ")(" + POS + a2 + ")(" + NOUN + a2 + ")",
                  "",
                  true);
          Rel = "CprepL";
          DepRelHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + NOUN + a2 + ")(" + POS + a2 + ")(" + NOUN + a2 + ")",
                      "$3",
                      "",
                      true));
          /* Single: [DET] ADJ [PRP]
          Corr: tag:NOUN*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:" + DET + a2 + ")(" + ADJ + a2 + ")(?:" + PRP + a2 + ")",
                  "",
                  true);
          Rel = "Single";
          Head(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + DET + a2 + ")(" + ADJ + a2 + ")(" + PRP + a2 + ")",
                      "$1$2$3",
                      "",
                      true));
          Corr(
              Pd.union()
                  .append(new Box[] {Casting.box("Head"), Casting.box("tag:NOUN")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          /* AdjnL: [PRP<lemma:as>] ADV [DET]  NOUN*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + PRP
                      + l
                      + "lemma:as\\|"
                      + r
                      + ")("
                      + ADV
                      + a2
                      + ")(?:"
                      + DET
                      + a2
                      + ")("
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "AdjnL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + PRP
                          + l
                          + "lemma:as\\|"
                          + r
                          + ")("
                          + ADV
                          + a2
                          + ")("
                          + DET
                          + a2
                          + ")("
                          + NOUN
                          + a2
                          + ")",
                      "$1$3$4",
                      "",
                      true));
          /* SpecL: DET DET [NOUN]
          NEXT
          SpecL: [DET] DET NOUN*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(" + DET + a2 + ")(" + DET + a2 + ")(?:" + NOUN + a2 + ")",
                  "",
                  true);
          Rel = "SpecL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:" + DET + a2 + ")(" + DET + a2 + ")(" + NOUN + a2 + ")",
                  "",
                  true);
          Rel = "SpecL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + DET + a2 + ")(" + DET + a2 + ")(" + NOUN + a2 + ")",
                      "$3",
                      "",
                      true));
          /* AdjnL: [DET] VERB<mode:P> NOUN
          NEXT
          SpecL: DET [VERB<mode:P>] NOUN*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:" + DET + a2 + ")(" + VERB + l + "mode:P\\|" + r + ")(" + NOUN + a2 + ")",
                  "",
                  true);
          Rel = "AdjnL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(" + DET + a2 + ")(?:" + VERB + l + "mode:P\\|" + r + ")(" + NOUN + a2 + ")",
                  "",
                  true);
          Rel = "SpecL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + DET + a2 + ")(" + VERB + l + "mode:P\\|" + r + ")(" + NOUN + a2 + ")",
                      "$3",
                      "",
                      true));
          /* SpecL: DET CARD|DATE*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(" + DET + a2 + ")(" + CARD + a2 + "|" + DATE + a2 + ")",
                  "",
                  true);
          Rel = "SpecL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + DET + a2 + ")(" + CARD + a2 + "|" + DATE + a2 + ")",
                      "$2",
                      "",
                      true));
          /* SpecL: DET NOUN|PRO
          Recursivity: 1*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(" + DET + a2 + ")(" + NOUN + a2 + "|" + PRO + a2 + ")",
                  "",
                  true);
          Rel = "SpecL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + DET + a2 + ")(" + NOUN + a2 + "|" + PRO + a2 + ")",
                      "$2",
                      "",
                      true));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(" + DET + a2 + ")(" + NOUN + a2 + "|" + PRO + a2 + ")",
                  "",
                  true);
          Rel = "SpecL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + DET + a2 + ")(" + NOUN + a2 + "|" + PRO + a2 + ")",
                      "$2",
                      "",
                      true));
          /* SpecL: PRO<lemma:what> NOUN*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(" + PRO + l + "lemma:what\\|" + r + ")(" + NOUN + a2 + ")",
                  "",
                  true);
          Rel = "SpecL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + PRO + l + "lemma:what\\|" + r + ")(" + NOUN + a2 + ")",
                      "$2",
                      "",
                      true));
          /* ClitL: PRO<token:$cliticopers> VERB
          Recursivity: 1*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(" + PRO + l + "token:" + cliticopers + "\\|" + r + ")(" + VERB + a2 + ")",
                  "",
                  true);
          Rel = "ClitL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + PRO + l + "token:" + cliticopers + "\\|" + r + ")(" + VERB + a2 + ")",
                      "$2",
                      "",
                      true));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(" + PRO + l + "token:" + cliticopers + "\\|" + r + ")(" + VERB + a2 + ")",
                  "",
                  true);
          Rel = "ClitL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + PRO + l + "token:" + cliticopers + "\\|" + r + ")(" + VERB + a2 + ")",
                      "$2",
                      "",
                      true));
          /* ClitR: VERB PRO<token:$cliticopers>
          Recursivity: 1*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(" + VERB + a2 + ")(" + PRO + l + "token:" + cliticopers + "\\|" + r + ")",
                  "",
                  true);
          Rel = "ClitR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + VERB + a2 + ")(" + PRO + l + "token:" + cliticopers + "\\|" + r + ")",
                      "$1",
                      "",
                      true));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(" + VERB + a2 + ")(" + PRO + l + "token:" + cliticopers + "\\|" + r + ")",
                  "",
                  true);
          Rel = "ClitR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + VERB + a2 + ")(" + PRO + l + "token:" + cliticopers + "\\|" + r + ")",
                      "$1",
                      "",
                      true));
          /* VSpecL: VERB<type:S> [ADV]? [ADV]? [ADV]? [ADV]? [ADV]? [ADV]? [ADV]? [ADV]? [ADV]? [ADV]? VERB<mode:P>
          Add: voice:passive
          Inherit: mode, person, tense, number*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + VERB
                      + l
                      + "type:S\\|"
                      + r
                      + ")(?:"
                      + ADV
                      + a2
                      + ")?(?:"
                      + ADV
                      + a2
                      + ")?(?:"
                      + ADV
                      + a2
                      + ")?(?:"
                      + ADV
                      + a2
                      + ")?(?:"
                      + ADV
                      + a2
                      + ")?(?:"
                      + ADV
                      + a2
                      + ")?(?:"
                      + ADV
                      + a2
                      + ")?(?:"
                      + ADV
                      + a2
                      + ")?(?:"
                      + ADV
                      + a2
                      + ")?(?:"
                      + ADV
                      + a2
                      + ")?("
                      + VERB
                      + l
                      + "mode:P\\|"
                      + r
                      + ")",
                  "",
                  true);
          Rel = "VSpecL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + VERB
                          + l
                          + "type:S\\|"
                          + r
                          + ")("
                          + ADV
                          + a2
                          + ")?("
                          + ADV
                          + a2
                          + ")?("
                          + ADV
                          + a2
                          + ")?("
                          + ADV
                          + a2
                          + ")?("
                          + ADV
                          + a2
                          + ")?("
                          + ADV
                          + a2
                          + ")?("
                          + ADV
                          + a2
                          + ")?("
                          + ADV
                          + a2
                          + ")?("
                          + ADV
                          + a2
                          + ")?("
                          + ADV
                          + a2
                          + ")?("
                          + VERB
                          + l
                          + "mode:P\\|"
                          + r
                          + ")",
                      "$2$3$4$5$6$7$8$9$10$11$12",
                      "",
                      true));
          Inherit(
              Pd.union()
                  .append(
                      new Box[] {Casting.box("DepHead"), Casting.box("mode,person,tense,number")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          Add(
              Pd.union()
                  .append(new Box[] {Casting.box("DepHead"), Casting.box("voice:passive")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          /* VSpecL: VERB<(type:S)|(lemma:ser|être|be)> [ADV]? [ADV]? [ADV]? [ADV]? [ADV]? [ADV]? [ADV]? [ADV]? [ADV]? [ADV]? VERB<mode:P>
          Add: voice:passive
          Inherit: mode, person, tense, number*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + VERB
                      + l
                      + "type:S\\|"
                      + r
                      + "|"
                      + VERB
                      + l
                      + "lemma:(?:ser|être|be)\\|"
                      + r
                      + ")(?:"
                      + ADV
                      + a2
                      + ")?(?:"
                      + ADV
                      + a2
                      + ")?(?:"
                      + ADV
                      + a2
                      + ")?(?:"
                      + ADV
                      + a2
                      + ")?(?:"
                      + ADV
                      + a2
                      + ")?(?:"
                      + ADV
                      + a2
                      + ")?(?:"
                      + ADV
                      + a2
                      + ")?(?:"
                      + ADV
                      + a2
                      + ")?(?:"
                      + ADV
                      + a2
                      + ")?(?:"
                      + ADV
                      + a2
                      + ")?("
                      + VERB
                      + l
                      + "mode:P\\|"
                      + r
                      + ")",
                  "",
                  true);
          Rel = "VSpecL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + VERB
                          + l
                          + "type:S\\|"
                          + r
                          + "|"
                          + VERB
                          + l
                          + "lemma:(?:ser|être|be)\\|"
                          + r
                          + ")("
                          + ADV
                          + a2
                          + ")?("
                          + ADV
                          + a2
                          + ")?("
                          + ADV
                          + a2
                          + ")?("
                          + ADV
                          + a2
                          + ")?("
                          + ADV
                          + a2
                          + ")?("
                          + ADV
                          + a2
                          + ")?("
                          + ADV
                          + a2
                          + ")?("
                          + ADV
                          + a2
                          + ")?("
                          + ADV
                          + a2
                          + ")?("
                          + ADV
                          + a2
                          + ")?("
                          + VERB
                          + l
                          + "mode:P\\|"
                          + r
                          + ")",
                      "$2$3$4$5$6$7$8$9$10$11$12",
                      "",
                      true));
          Inherit(
              Pd.union()
                  .append(
                      new Box[] {Casting.box("DepHead"), Casting.box("mode,person,tense,number")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          Add(
              Pd.union()
                  .append(new Box[] {Casting.box("DepHead"), Casting.box("voice:passive")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          /* VSpecL: VERB<(type:A)|(lemma:ter|haver|haber|avoir|have)> [ADV]? [ADV]? [ADV]? [ADV]? [ADV]? [ADV]? [ADV]? [ADV]? [ADV]? [ADV]? VERB<mode:P>
          Add: type:perfect
          Inherit: mode, person, tense, number*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + VERB
                      + l
                      + "type:A\\|"
                      + r
                      + "|"
                      + VERB
                      + l
                      + "lemma:(?:ter|haver|haber|avoir|have)\\|"
                      + r
                      + ")(?:"
                      + ADV
                      + a2
                      + ")?(?:"
                      + ADV
                      + a2
                      + ")?(?:"
                      + ADV
                      + a2
                      + ")?(?:"
                      + ADV
                      + a2
                      + ")?(?:"
                      + ADV
                      + a2
                      + ")?(?:"
                      + ADV
                      + a2
                      + ")?(?:"
                      + ADV
                      + a2
                      + ")?(?:"
                      + ADV
                      + a2
                      + ")?(?:"
                      + ADV
                      + a2
                      + ")?(?:"
                      + ADV
                      + a2
                      + ")?("
                      + VERB
                      + l
                      + "mode:P\\|"
                      + r
                      + ")",
                  "",
                  true);
          Rel = "VSpecL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + VERB
                          + l
                          + "type:A\\|"
                          + r
                          + "|"
                          + VERB
                          + l
                          + "lemma:(?:ter|haver|haber|avoir|have)\\|"
                          + r
                          + ")("
                          + ADV
                          + a2
                          + ")?("
                          + ADV
                          + a2
                          + ")?("
                          + ADV
                          + a2
                          + ")?("
                          + ADV
                          + a2
                          + ")?("
                          + ADV
                          + a2
                          + ")?("
                          + ADV
                          + a2
                          + ")?("
                          + ADV
                          + a2
                          + ")?("
                          + ADV
                          + a2
                          + ")?("
                          + ADV
                          + a2
                          + ")?("
                          + ADV
                          + a2
                          + ")?("
                          + VERB
                          + l
                          + "mode:P\\|"
                          + r
                          + ")",
                      "$2$3$4$5$6$7$8$9$10$11$12",
                      "",
                      true));
          Inherit(
              Pd.union()
                  .append(
                      new Box[] {Casting.box("DepHead"), Casting.box("mode,person,tense,number")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          Add(
              Pd.union()
                  .append(new Box[] {Casting.box("DepHead"), Casting.box("type:perfect")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          /* VSpecL: VERB<lemma:do> [ADV]? [ADV]? [ADV]? [ADV]? [ADV]? [ADV]? [ADV]? [ADV]? [ADV]? [ADV]? VERB
          Inherit: mode, person, tense, number*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + VERB
                      + l
                      + "lemma:do\\|"
                      + r
                      + ")(?:"
                      + ADV
                      + a2
                      + ")?(?:"
                      + ADV
                      + a2
                      + ")?(?:"
                      + ADV
                      + a2
                      + ")?(?:"
                      + ADV
                      + a2
                      + ")?(?:"
                      + ADV
                      + a2
                      + ")?(?:"
                      + ADV
                      + a2
                      + ")?(?:"
                      + ADV
                      + a2
                      + ")?(?:"
                      + ADV
                      + a2
                      + ")?(?:"
                      + ADV
                      + a2
                      + ")?(?:"
                      + ADV
                      + a2
                      + ")?("
                      + VERB
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "VSpecL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + VERB
                          + l
                          + "lemma:do\\|"
                          + r
                          + ")("
                          + ADV
                          + a2
                          + ")?("
                          + ADV
                          + a2
                          + ")?("
                          + ADV
                          + a2
                          + ")?("
                          + ADV
                          + a2
                          + ")?("
                          + ADV
                          + a2
                          + ")?("
                          + ADV
                          + a2
                          + ")?("
                          + ADV
                          + a2
                          + ")?("
                          + ADV
                          + a2
                          + ")?("
                          + ADV
                          + a2
                          + ")?("
                          + ADV
                          + a2
                          + ")?("
                          + VERB
                          + a2
                          + ")",
                      "$2$3$4$5$6$7$8$9$10$11$12",
                      "",
                      true));
          Inherit(
              Pd.union()
                  .append(
                      new Box[] {Casting.box("DepHead"), Casting.box("mode,person,tense,number")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          /* VSpecL: VERB<lemma:$VModalEN> [ADV]? [ADV]? [ADV]? [ADV]? [ADV]? [ADV]? [ADV]? [ADV]? [ADV]? [ADV]? VERB
          Inherit: mode, person, tense, number*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(" + VERB + l + "lemma:" + VModalEN + "\\|" + r + ")(?:" + ADV + a2 + ")?(?:"
                      + ADV + a2 + ")?(?:" + ADV + a2 + ")?(?:" + ADV + a2 + ")?(?:" + ADV + a2
                      + ")?(?:" + ADV + a2 + ")?(?:" + ADV + a2 + ")?(?:" + ADV + a2 + ")?(?:" + ADV
                      + a2 + ")?(?:" + ADV + a2 + ")?(" + VERB + a2 + ")",
                  "",
                  true);
          Rel = "VSpecL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + VERB + l + "lemma:" + VModalEN + "\\|" + r + ")(" + ADV + a2 + ")?("
                          + ADV + a2 + ")?(" + ADV + a2 + ")?(" + ADV + a2 + ")?(" + ADV + a2
                          + ")?(" + ADV + a2 + ")?(" + ADV + a2 + ")?(" + ADV + a2 + ")?(" + ADV
                          + a2 + ")?(" + ADV + a2 + ")?(" + VERB + a2 + ")",
                      "$2$3$4$5$6$7$8$9$10$11$12",
                      "",
                      true));
          Inherit(
              Pd.union()
                  .append(
                      new Box[] {Casting.box("DepHead"), Casting.box("mode,person,tense,number")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          /* VSpecL: VERB [ADV]? [ADV]? [ADV]? [ADV]? [ADV]? [ADV]? [ADV]? [ADV]? [ADV]? [ADV]? VERB<mode:G>
          Inherit: mode, person, tense, number*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + VERB
                      + a2
                      + ")(?:"
                      + ADV
                      + a2
                      + ")?(?:"
                      + ADV
                      + a2
                      + ")?(?:"
                      + ADV
                      + a2
                      + ")?(?:"
                      + ADV
                      + a2
                      + ")?(?:"
                      + ADV
                      + a2
                      + ")?(?:"
                      + ADV
                      + a2
                      + ")?(?:"
                      + ADV
                      + a2
                      + ")?(?:"
                      + ADV
                      + a2
                      + ")?(?:"
                      + ADV
                      + a2
                      + ")?(?:"
                      + ADV
                      + a2
                      + ")?("
                      + VERB
                      + l
                      + "mode:G\\|"
                      + r
                      + ")",
                  "",
                  true);
          Rel = "VSpecL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + VERB
                          + a2
                          + ")("
                          + ADV
                          + a2
                          + ")?("
                          + ADV
                          + a2
                          + ")?("
                          + ADV
                          + a2
                          + ")?("
                          + ADV
                          + a2
                          + ")?("
                          + ADV
                          + a2
                          + ")?("
                          + ADV
                          + a2
                          + ")?("
                          + ADV
                          + a2
                          + ")?("
                          + ADV
                          + a2
                          + ")?("
                          + ADV
                          + a2
                          + ")?("
                          + ADV
                          + a2
                          + ")?("
                          + VERB
                          + l
                          + "mode:G\\|"
                          + r
                          + ")",
                      "$2$3$4$5$6$7$8$9$10$11$12",
                      "",
                      true));
          Inherit(
              Pd.union()
                  .append(
                      new Box[] {Casting.box("DepHead"), Casting.box("mode,person,tense,number")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          /* VSpecLocL: VERB [ADV]? [ADV]? [ADV]? [ADV]? [ADV]? [ADV]? [ADV]? [ADV]? [ADV]? [ADV]? PRP<lemma:$PrepLocs> [ADV]? [ADV]? [ADV]? [ADV]? [ADV]? [ADV]? [ADV]? [ADV]? [ADV]? [ADV]? VERB
          Inherit: mode, person, tense, number*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(" + VERB + a2 + ")(?:" + ADV + a2 + ")?(?:" + ADV + a2 + ")?(?:" + ADV + a2
                      + ")?(?:" + ADV + a2 + ")?(?:" + ADV + a2 + ")?(?:" + ADV + a2 + ")?(?:" + ADV
                      + a2 + ")?(?:" + ADV + a2 + ")?(?:" + ADV + a2 + ")?(?:" + ADV + a2 + ")?("
                      + PRP + l + "lemma:" + PrepLocs + "\\|" + r + ")(?:" + ADV + a2 + ")?(?:"
                      + ADV + a2 + ")?(?:" + ADV + a2 + ")?(?:" + ADV + a2 + ")?(?:" + ADV + a2
                      + ")?(?:" + ADV + a2 + ")?(?:" + ADV + a2 + ")?(?:" + ADV + a2 + ")?(?:" + ADV
                      + a2 + ")?(?:" + ADV + a2 + ")?(" + VERB + a2 + ")",
                  "",
                  true);
          Rel = "VSpecLocL";
          DepRelHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + VERB + a2 + ")(" + ADV + a2 + ")?(" + ADV + a2 + ")?(" + ADV + a2
                          + ")?(" + ADV + a2 + ")?(" + ADV + a2 + ")?(" + ADV + a2 + ")?(" + ADV
                          + a2 + ")?(" + ADV + a2 + ")?(" + ADV + a2 + ")?(" + ADV + a2 + ")?("
                          + PRP + l + "lemma:" + PrepLocs + "\\|" + r + ")(" + ADV + a2 + ")?("
                          + ADV + a2 + ")?(" + ADV + a2 + ")?(" + ADV + a2 + ")?(" + ADV + a2
                          + ")?(" + ADV + a2 + ")?(" + ADV + a2 + ")?(" + ADV + a2 + ")?(" + ADV
                          + a2 + ")?(" + ADV + a2 + ")?(" + VERB + a2 + ")",
                      "$2$3$4$5$6$7$8$9$10$11$13$14$15$16$17$18$19$20$21$22$23",
                      "",
                      true));
          Inherit(
              Pd.union()
                  .append(
                      new Box[] {
                        Casting.box("DepRelHead"), Casting.box("mode,person,tense,number")
                      })
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          /* PunctL: [ADV<pos:0>] Fc VERB
          NEXT
          AdjnL: ADV<pos:0> [Fc] VERB*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:" + ADV + l + "pos:0\\|" + r + ")(" + Fc + a2 + ")(" + VERB + a2 + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(" + ADV + l + "pos:0\\|" + r + ")(?:" + Fc + a2 + ")(" + VERB + a2 + ")",
                  "",
                  true);
          Rel = "AdjnL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + ADV + l + "pos:0\\|" + r + ")(" + Fc + a2 + ")(" + VERB + a2 + ")",
                      "$3",
                      "",
                      true));
          /* PunctL: Fc [ADV] [Fc]? VERB
          NEXT
          PunctL: [Fc] [ADV] Fc VERB
          NEXT
          AdjnL: [Fc] ADV [Fc]? VERB*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(" + Fc + a2 + ")(?:" + ADV + a2 + ")(?:" + Fc + a2 + ")?(" + VERB + a2 + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:" + Fc + a2 + ")(?:" + ADV + a2 + ")(" + Fc + a2 + ")(" + VERB + a2 + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:" + Fc + a2 + ")(" + ADV + a2 + ")(?:" + Fc + a2 + ")?(" + VERB + a2 + ")",
                  "",
                  true);
          Rel = "AdjnL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + Fc + a2 + ")(" + ADV + a2 + ")(" + Fc + a2 + ")?(" + VERB + a2 + ")",
                      "$4",
                      "",
                      true));
          /* PunctR:  VERB [Fc]? [ADV] Fc
          NEXT
          PunctR: VERB Fc [ADV] [Fc]
          NEXT
          AdjnR: VERB [Fc]? ADV [Fc]*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(" + VERB + a2 + ")(?:" + Fc + a2 + ")?(?:" + ADV + a2 + ")(" + Fc + a2 + ")",
                  "",
                  true);
          Rel = "PunctR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(" + VERB + a2 + ")(" + Fc + a2 + ")(?:" + ADV + a2 + ")(?:" + Fc + a2 + ")",
                  "",
                  true);
          Rel = "PunctR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(" + VERB + a2 + ")(?:" + Fc + a2 + ")?(" + ADV + a2 + ")(?:" + Fc + a2 + ")",
                  "",
                  true);
          Rel = "AdjnR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + VERB + a2 + ")(" + Fc + a2 + ")?(" + ADV + a2 + ")(" + Fc + a2 + ")",
                      "$1",
                      "",
                      true));
          /* AdjnL:  ADV  VERB
          Recursivity: 1*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags), "(" + ADV + a2 + ")(" + VERB + a2 + ")", "", true);
          Rel = "AdjnL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + ADV + a2 + ")(" + VERB + a2 + ")",
                      "$2",
                      "",
                      true));
          temp =
              Regex.matcher(
                  Casting.toString(listTags), "(" + ADV + a2 + ")(" + VERB + a2 + ")", "", true);
          Rel = "AdjnL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + ADV + a2 + ")(" + VERB + a2 + ")",
                      "$2",
                      "",
                      true));
          /* AdjnR: VERB [NOUN|PRO<type:D|P|I|X>]? ADV
          Recursivity: 1*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + VERB
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")?("
                      + ADV
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "AdjnR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + VERB
                          + a2
                          + ")("
                          + NOUN
                          + a2
                          + "|"
                          + PRO
                          + l
                          + "type:(?:D|P|I|X)\\|"
                          + r
                          + ")?("
                          + ADV
                          + a2
                          + ")",
                      "$1$2",
                      "",
                      true));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + VERB
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")?("
                      + ADV
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "AdjnR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + VERB
                          + a2
                          + ")("
                          + NOUN
                          + a2
                          + "|"
                          + PRO
                          + l
                          + "type:(?:D|P|I|X)\\|"
                          + r
                          + ")?("
                          + ADV
                          + a2
                          + ")",
                      "$1$2",
                      "",
                      true));
          /* >: VERB [NOUN]? PCLE*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(" + VERB + a2 + ")(?:" + NOUN + a2 + ")?(" + PCLE + a2 + ")",
                  "",
                  true);
          Rel = ">";
          HeadDep_lex(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + VERB + a2 + ")(" + NOUN + a2 + ")?(" + PCLE + a2 + ")",
                      "$1$2",
                      "",
                      true));
          LEX(new Box[0]);
          /* CoordL: PRP [NOUN] CONJ<lemma:and|or|y|e|et|o|ou> [PRP] [NOUN]
          NEXT
          TermR: [PRP] [NOUN] [CONJ<lemma:and|or|y|e|et|o|ou>] PRP NOUN
          NEXT
          TermR: PRP NOUN [CONJ<lemma:and|or|y|e|et|o|ou>] [PRP] [NOUN]
          NEXT
          CoordR: [PRP] [NOUN] CONJ<lemma:and|or|y|e|et|o|ou> PRP [NOUN]
          Add: coord:cprep*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")(?:"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")("
                      + PRP
                      + a2
                      + ")("
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "TermR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + PRP
                      + a2
                      + ")("
                      + NOUN
                      + a2
                      + ")(?:"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "TermR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")("
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + PRP
                          + a2
                          + ")("
                          + NOUN
                          + a2
                          + ")("
                          + CONJ
                          + l
                          + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                          + r
                          + ")("
                          + PRP
                          + a2
                          + ")("
                          + NOUN
                          + a2
                          + ")",
                      "$3",
                      "",
                      true));
          Add(
              Pd.union()
                  .append(new Box[] {Casting.box("HeadDep"), Casting.box("coord:cprep")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          /* TermR: PRP NOUN [Fc] [PRP] [NOUN] [Fc] [CONJ<lemma:and|or|y|e|et|o|ou>] [PRP] [NOUN]
          NEXT
          PunctL: [PRP] [NOUN] Fc [PRP] [NOUN] [Fc] CONJ<lemma:and|or|y|e|et|o|ou> [PRP] [NOUN]
          NEXT
          CoordL: PRP [NOUN] [Fc] [PRP] [NOUN] [Fc] CONJ<lemma:and|or|y|e|et|o|ou> [PRP] [NOUN]
          Recursivity: 3*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + PRP
                      + a2
                      + ")("
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")(?:"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "TermR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")("
                      + Fc
                      + a2
                      + ")(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + PRP
                          + a2
                          + ")("
                          + NOUN
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + PRP
                          + a2
                          + ")("
                          + NOUN
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + CONJ
                          + l
                          + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                          + r
                          + ")("
                          + PRP
                          + a2
                          + ")("
                          + NOUN
                          + a2
                          + ")",
                      "$4$5$6$7$8$9",
                      "",
                      true));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + PRP
                      + a2
                      + ")("
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")(?:"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "TermR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")("
                      + Fc
                      + a2
                      + ")(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + PRP
                          + a2
                          + ")("
                          + NOUN
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + PRP
                          + a2
                          + ")("
                          + NOUN
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + CONJ
                          + l
                          + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                          + r
                          + ")("
                          + PRP
                          + a2
                          + ")("
                          + NOUN
                          + a2
                          + ")",
                      "$4$5$6$7$8$9",
                      "",
                      true));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + PRP
                      + a2
                      + ")("
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")(?:"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "TermR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")("
                      + Fc
                      + a2
                      + ")(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + PRP
                          + a2
                          + ")("
                          + NOUN
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + PRP
                          + a2
                          + ")("
                          + NOUN
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + CONJ
                          + l
                          + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                          + r
                          + ")("
                          + PRP
                          + a2
                          + ")("
                          + NOUN
                          + a2
                          + ")",
                      "$4$5$6$7$8$9",
                      "",
                      true));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + PRP
                      + a2
                      + ")("
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")(?:"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "TermR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")("
                      + Fc
                      + a2
                      + ")(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + PRP
                          + a2
                          + ")("
                          + NOUN
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + PRP
                          + a2
                          + ")("
                          + NOUN
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + CONJ
                          + l
                          + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                          + r
                          + ")("
                          + PRP
                          + a2
                          + ")("
                          + NOUN
                          + a2
                          + ")",
                      "$4$5$6$7$8$9",
                      "",
                      true));
          /* CoordL: [Fc]? PRP [NOUN] [Fc] CONJ<lemma:and|or|y|e|et|o|ou> [PRP] [NOUN]
          NEXT
          TermR: [Fc]? [PRP] [NOUN] [Fc] [CONJ<lemma:and|or|y|e|et|o|ou>] PRP NOUN
          NEXT
          TermR: [Fc]? PRP NOUN [Fc] [CONJ<lemma:and|or|y|e|et|o|ou>] [PRP] [NOUN]
          NEXT
          PunctL: [Fc]? [PRP] [NOUN] Fc CONJ<lemma:and|or|y|e|et|o|ou> [PRP] [NOUN]
          NEXT
          CoordR: [Fc]? [PRP] [NOUN] [Fc] CONJ<lemma:and|or|y|e|et|o|ou> PRP [NOUN]
          Add: coord:cprep*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + Fc
                      + a2
                      + ")?("
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + Fc
                      + a2
                      + ")?(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")(?:"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")("
                      + PRP
                      + a2
                      + ")("
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "TermR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + Fc
                      + a2
                      + ")?("
                      + PRP
                      + a2
                      + ")("
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")(?:"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "TermR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + Fc
                      + a2
                      + ")?(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")("
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + Fc
                      + a2
                      + ")?(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")("
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + Fc
                          + a2
                          + ")?("
                          + PRP
                          + a2
                          + ")("
                          + NOUN
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + CONJ
                          + l
                          + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                          + r
                          + ")("
                          + PRP
                          + a2
                          + ")("
                          + NOUN
                          + a2
                          + ")",
                      "$1$5",
                      "",
                      true));
          Add(
              Pd.union()
                  .append(new Box[] {Casting.box("HeadDep"), Casting.box("coord:cprep")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          /* CprepR: [NOUNSINGLE] [PRP] [NOUNSINGLE] [PRP] [NOUNSINGLE] [PRP] [NOUNSINGLE] [PRP] NOUNSINGLE PRP<lemma:$PrepRA> NOUNSINGLE|PRO<type:D|P|I|X>*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + NOUNSINGLE
                      + a2
                      + ")(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUNSINGLE
                      + a2
                      + ")(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUNSINGLE
                      + a2
                      + ")(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUNSINGLE
                      + a2
                      + ")(?:"
                      + PRP
                      + a2
                      + ")("
                      + NOUNSINGLE
                      + a2
                      + ")("
                      + PRP
                      + l
                      + "lemma:"
                      + PrepRA
                      + "\\|"
                      + r
                      + ")("
                      + NOUNSINGLE
                      + a2
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")",
                  "",
                  true);
          Rel = "CprepR";
          HeadRelDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + NOUNSINGLE
                          + a2
                          + ")("
                          + PRP
                          + a2
                          + ")("
                          + NOUNSINGLE
                          + a2
                          + ")("
                          + PRP
                          + a2
                          + ")("
                          + NOUNSINGLE
                          + a2
                          + ")("
                          + PRP
                          + a2
                          + ")("
                          + NOUNSINGLE
                          + a2
                          + ")("
                          + PRP
                          + a2
                          + ")("
                          + NOUNSINGLE
                          + a2
                          + ")("
                          + PRP
                          + l
                          + "lemma:"
                          + PrepRA
                          + "\\|"
                          + r
                          + ")("
                          + NOUNSINGLE
                          + a2
                          + "|"
                          + PRO
                          + l
                          + "type:(?:D|P|I|X)\\|"
                          + r
                          + ")",
                      "$1$2$3$4$5$6$7$8$9",
                      "",
                      true));
          /* CprepR: [NOUNSINGLE] [PRP] [NOUNSINGLE] [PRP] [NOUNSINGLE] [PRP] NOUNSINGLE PRP<lemma:$PrepRA> NOUNSINGLE|PRO<type:D|P|I|X>*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + NOUNSINGLE
                      + a2
                      + ")(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUNSINGLE
                      + a2
                      + ")(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUNSINGLE
                      + a2
                      + ")(?:"
                      + PRP
                      + a2
                      + ")("
                      + NOUNSINGLE
                      + a2
                      + ")("
                      + PRP
                      + l
                      + "lemma:"
                      + PrepRA
                      + "\\|"
                      + r
                      + ")("
                      + NOUNSINGLE
                      + a2
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")",
                  "",
                  true);
          Rel = "CprepR";
          HeadRelDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + NOUNSINGLE
                          + a2
                          + ")("
                          + PRP
                          + a2
                          + ")("
                          + NOUNSINGLE
                          + a2
                          + ")("
                          + PRP
                          + a2
                          + ")("
                          + NOUNSINGLE
                          + a2
                          + ")("
                          + PRP
                          + a2
                          + ")("
                          + NOUNSINGLE
                          + a2
                          + ")("
                          + PRP
                          + l
                          + "lemma:"
                          + PrepRA
                          + "\\|"
                          + r
                          + ")("
                          + NOUNSINGLE
                          + a2
                          + "|"
                          + PRO
                          + l
                          + "type:(?:D|P|I|X)\\|"
                          + r
                          + ")",
                      "$1$2$3$4$5$6$7",
                      "",
                      true));
          /* CprepR: [NOUNSINGLE] [PRP] [NOUNSINGLE] [PRP] NOUNSINGLE PRP<lemma:$PrepRA> NOUNSINGLE|PRO<type:D|P|I|X>*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + NOUNSINGLE
                      + a2
                      + ")(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUNSINGLE
                      + a2
                      + ")(?:"
                      + PRP
                      + a2
                      + ")("
                      + NOUNSINGLE
                      + a2
                      + ")("
                      + PRP
                      + l
                      + "lemma:"
                      + PrepRA
                      + "\\|"
                      + r
                      + ")("
                      + NOUNSINGLE
                      + a2
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")",
                  "",
                  true);
          Rel = "CprepR";
          HeadRelDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + NOUNSINGLE
                          + a2
                          + ")("
                          + PRP
                          + a2
                          + ")("
                          + NOUNSINGLE
                          + a2
                          + ")("
                          + PRP
                          + a2
                          + ")("
                          + NOUNSINGLE
                          + a2
                          + ")("
                          + PRP
                          + l
                          + "lemma:"
                          + PrepRA
                          + "\\|"
                          + r
                          + ")("
                          + NOUNSINGLE
                          + a2
                          + "|"
                          + PRO
                          + l
                          + "type:(?:D|P|I|X)\\|"
                          + r
                          + ")",
                      "$1$2$3$4$5",
                      "",
                      true));
          /* CprepR: [NOUNSINGLE] [PRP] NOUNSINGLE PRP<lemma:$PrepRA> NOUNSINGLE|PRO<type:D|P|I|X>*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + NOUNSINGLE
                      + a2
                      + ")(?:"
                      + PRP
                      + a2
                      + ")("
                      + NOUNSINGLE
                      + a2
                      + ")("
                      + PRP
                      + l
                      + "lemma:"
                      + PrepRA
                      + "\\|"
                      + r
                      + ")("
                      + NOUNSINGLE
                      + a2
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")",
                  "",
                  true);
          Rel = "CprepR";
          HeadRelDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + NOUNSINGLE
                          + a2
                          + ")("
                          + PRP
                          + a2
                          + ")("
                          + NOUNSINGLE
                          + a2
                          + ")("
                          + PRP
                          + l
                          + "lemma:"
                          + PrepRA
                          + "\\|"
                          + r
                          + ")("
                          + NOUNSINGLE
                          + a2
                          + "|"
                          + PRO
                          + l
                          + "type:(?:D|P|I|X)\\|"
                          + r
                          + ")",
                      "$1$2$3",
                      "",
                      true));
          /* CprepR: NOUNSINGLE PRP<lemma:$PrepRA> NOUNSINGLE|PRO<type:D|P|I|X>*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + NOUNSINGLE
                      + a2
                      + ")("
                      + PRP
                      + l
                      + "lemma:"
                      + PrepRA
                      + "\\|"
                      + r
                      + ")("
                      + NOUNSINGLE
                      + a2
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")",
                  "",
                  true);
          Rel = "CprepR";
          HeadRelDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + NOUNSINGLE
                          + a2
                          + ")("
                          + PRP
                          + l
                          + "lemma:"
                          + PrepRA
                          + "\\|"
                          + r
                          + ")("
                          + NOUNSINGLE
                          + a2
                          + "|"
                          + PRO
                          + l
                          + "type:(?:D|P|I|X)\\|"
                          + r
                          + ")",
                      "$1",
                      "",
                      true));
          /* CprepR: CARD<lemma:uno|one|un|um> PRP NOUNSINGLE|PRO<type:D|P|I|X>
          Add: tag:PRO*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + CARD
                      + l
                      + "lemma:(?:uno|one|un|um)\\|"
                      + r
                      + ")("
                      + PRP
                      + a2
                      + ")("
                      + NOUNSINGLE
                      + a2
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")",
                  "",
                  true);
          Rel = "CprepR";
          HeadRelDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + CARD
                          + l
                          + "lemma:(?:uno|one|un|um)\\|"
                          + r
                          + ")("
                          + PRP
                          + a2
                          + ")("
                          + NOUNSINGLE
                          + a2
                          + "|"
                          + PRO
                          + l
                          + "type:(?:D|P|I|X)\\|"
                          + r
                          + ")",
                      "$1",
                      "",
                      true));
          Add(
              Pd.union()
                  .append(new Box[] {Casting.box("HeadRelDep"), Casting.box("tag:PRO")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          /* CoordL: NOUN CONJ<(type:C)|(lemma:and|or|y|e|et|o|ou)> [NOUN]
          NEXT
          CoordR: [NOUN]  CONJ<(type:C)|(lemma:and|or|y|e|et|o|ou)> NOUN
          Add: coord:noun*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + NOUN
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + NOUN
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")("
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + NOUN
                          + a2
                          + ")("
                          + CONJ
                          + l
                          + "type:C\\|"
                          + r
                          + "|"
                          + CONJ
                          + l
                          + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                          + r
                          + ")("
                          + NOUN
                          + a2
                          + ")",
                      "$2",
                      "",
                      true));
          Add(
              Pd.union()
                  .append(new Box[] {Casting.box("HeadDep"), Casting.box("coord:noun")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          /* CoordL: NOUN [Fc] [NOUN] [Fc] CONJ<(type:C)|(lemma:and|or|y|e|et|o|ou)> [NOUN]
          NEXT
          PunctL:  [NOUN] Fc [NOUN] [Fc] CONJ<(type:C)|(lemma:and|or|y|e|et|o|ou)> [NOUN]
          Recursivity: 10*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + NOUN
                      + a2
                      + ")("
                      + Fc
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + NOUN
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + NOUN
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + CONJ
                          + l
                          + "type:C\\|"
                          + r
                          + "|"
                          + CONJ
                          + l
                          + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                          + r
                          + ")("
                          + NOUN
                          + a2
                          + ")",
                      "$3$4$5$6",
                      "",
                      true));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + NOUN
                      + a2
                      + ")("
                      + Fc
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + NOUN
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + NOUN
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + CONJ
                          + l
                          + "type:C\\|"
                          + r
                          + "|"
                          + CONJ
                          + l
                          + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                          + r
                          + ")("
                          + NOUN
                          + a2
                          + ")",
                      "$3$4$5$6",
                      "",
                      true));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + NOUN
                      + a2
                      + ")("
                      + Fc
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + NOUN
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + NOUN
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + CONJ
                          + l
                          + "type:C\\|"
                          + r
                          + "|"
                          + CONJ
                          + l
                          + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                          + r
                          + ")("
                          + NOUN
                          + a2
                          + ")",
                      "$3$4$5$6",
                      "",
                      true));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + NOUN
                      + a2
                      + ")("
                      + Fc
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + NOUN
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + NOUN
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + CONJ
                          + l
                          + "type:C\\|"
                          + r
                          + "|"
                          + CONJ
                          + l
                          + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                          + r
                          + ")("
                          + NOUN
                          + a2
                          + ")",
                      "$3$4$5$6",
                      "",
                      true));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + NOUN
                      + a2
                      + ")("
                      + Fc
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + NOUN
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + NOUN
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + CONJ
                          + l
                          + "type:C\\|"
                          + r
                          + "|"
                          + CONJ
                          + l
                          + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                          + r
                          + ")("
                          + NOUN
                          + a2
                          + ")",
                      "$3$4$5$6",
                      "",
                      true));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + NOUN
                      + a2
                      + ")("
                      + Fc
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + NOUN
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + NOUN
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + CONJ
                          + l
                          + "type:C\\|"
                          + r
                          + "|"
                          + CONJ
                          + l
                          + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                          + r
                          + ")("
                          + NOUN
                          + a2
                          + ")",
                      "$3$4$5$6",
                      "",
                      true));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + NOUN
                      + a2
                      + ")("
                      + Fc
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + NOUN
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + NOUN
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + CONJ
                          + l
                          + "type:C\\|"
                          + r
                          + "|"
                          + CONJ
                          + l
                          + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                          + r
                          + ")("
                          + NOUN
                          + a2
                          + ")",
                      "$3$4$5$6",
                      "",
                      true));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + NOUN
                      + a2
                      + ")("
                      + Fc
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + NOUN
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + NOUN
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + CONJ
                          + l
                          + "type:C\\|"
                          + r
                          + "|"
                          + CONJ
                          + l
                          + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                          + r
                          + ")("
                          + NOUN
                          + a2
                          + ")",
                      "$3$4$5$6",
                      "",
                      true));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + NOUN
                      + a2
                      + ")("
                      + Fc
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + NOUN
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + NOUN
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + CONJ
                          + l
                          + "type:C\\|"
                          + r
                          + "|"
                          + CONJ
                          + l
                          + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                          + r
                          + ")("
                          + NOUN
                          + a2
                          + ")",
                      "$3$4$5$6",
                      "",
                      true));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + NOUN
                      + a2
                      + ")("
                      + Fc
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + NOUN
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + NOUN
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + CONJ
                          + l
                          + "type:C\\|"
                          + r
                          + "|"
                          + CONJ
                          + l
                          + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                          + r
                          + ")("
                          + NOUN
                          + a2
                          + ")",
                      "$3$4$5$6",
                      "",
                      true));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + NOUN
                      + a2
                      + ")("
                      + Fc
                      + a2
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + NOUN
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + NOUN
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + CONJ
                          + l
                          + "type:C\\|"
                          + r
                          + "|"
                          + CONJ
                          + l
                          + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                          + r
                          + ")("
                          + NOUN
                          + a2
                          + ")",
                      "$3$4$5$6",
                      "",
                      true));
          /* CoordL: NOUN [Fc] CONJ<(type:C)|(lemma:and|or|y|e|et|o|ou)> [NOUN]
          NEXT
          PunctL: [NOUN] Fc CONJ<(type:C)|(lemma:and|or|y|e|et|o|ou)> [NOUN]
          NEXT
          CoordR:  [NOUN] [Fc] CONJ<(type:C)|(lemma:and|or|y|e|et|o|ou)> NOUN
          Add: coord:noun*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + NOUN
                      + a2
                      + ")("
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + NOUN
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")("
                      + NOUN
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + NOUN
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + CONJ
                          + l
                          + "type:C\\|"
                          + r
                          + "|"
                          + CONJ
                          + l
                          + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                          + r
                          + ")("
                          + NOUN
                          + a2
                          + ")",
                      "$3",
                      "",
                      true));
          Add(
              Pd.union()
                  .append(new Box[] {Casting.box("HeadDep"), Casting.box("coord:noun")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          /* PunctL: [NOUNCOORD|PRO<type:D|P|I|X>] Fc|Fpa|Fca NOUNCOORD|PRO<type:D|P|I|X>|CARD [Fc|Fpt|Fct]
          NEXT
          PunctR: [NOUNCOORD|PRO<type:D|P|I|X>] [Fc|Fpa|Fca] NOUNCOORD|PRO<type:D|P|I|X>|CARD Fc|Fpt|Fct
          NEXT
          AdjnR: NOUNCOORD|PRO<type:D|P|I|X> [Fc|Fpa|Fca] NOUNCOORD|PRO<type:D|P|I|X>|CARD [Fc|Fpt|Fct]*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")("
                      + Fc
                      + a2
                      + "|"
                      + Fpa
                      + a2
                      + "|Fca)("
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + "|"
                      + CARD
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + "|"
                      + Fpt
                      + a2
                      + "|Fct)",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")(?:"
                      + Fc
                      + a2
                      + "|"
                      + Fpa
                      + a2
                      + "|Fca)("
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + "|"
                      + CARD
                      + a2
                      + ")("
                      + Fc
                      + a2
                      + "|"
                      + Fpt
                      + a2
                      + "|Fct)",
                  "",
                  true);
          Rel = "PunctR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")(?:"
                      + Fc
                      + a2
                      + "|"
                      + Fpa
                      + a2
                      + "|Fca)("
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + "|"
                      + CARD
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + "|"
                      + Fpt
                      + a2
                      + "|Fct)",
                  "",
                  true);
          Rel = "AdjnR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + NOUNCOORD
                          + "|"
                          + PRO
                          + l
                          + "type:(?:D|P|I|X)\\|"
                          + r
                          + ")("
                          + Fc
                          + a2
                          + "|"
                          + Fpa
                          + a2
                          + "|Fca)("
                          + NOUNCOORD
                          + "|"
                          + PRO
                          + l
                          + "type:(?:D|P|I|X)\\|"
                          + r
                          + "|"
                          + CARD
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + "|"
                          + Fpt
                          + a2
                          + "|Fct)",
                      "$1",
                      "",
                      true));
          /* PunctL: [NOUNCOORD|PRO<type:D|P|I|X>] Fc|Fpa|Fca [PRP] NOUNCOORD|PRO<type:D|P|I|X>|CARD [Fc|Fpt|Fct]
          NEXT
          PunctR: [NOUNCOORD|PRO<type:D|P|I|X>] [Fc|Fpa|Fca] [PRP] NOUNCOORD|PRO<type:D|P|I|X>|CARD Fc|Fpt|Fct
          NEXT
          CprepR: NOUNCOORD|PRO<type:D|P|I|X> [Fc|Fpa|Fca] PRP NOUNCOORD|PRO<type:D|P|I|X>|CARD [Fc|Fpt|Fct]*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")("
                      + Fc
                      + a2
                      + "|"
                      + Fpa
                      + a2
                      + "|Fca)(?:"
                      + PRP
                      + a2
                      + ")("
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + "|"
                      + CARD
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + "|"
                      + Fpt
                      + a2
                      + "|Fct)",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")(?:"
                      + Fc
                      + a2
                      + "|"
                      + Fpa
                      + a2
                      + "|Fca)(?:"
                      + PRP
                      + a2
                      + ")("
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + "|"
                      + CARD
                      + a2
                      + ")("
                      + Fc
                      + a2
                      + "|"
                      + Fpt
                      + a2
                      + "|Fct)",
                  "",
                  true);
          Rel = "PunctR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")(?:"
                      + Fc
                      + a2
                      + "|"
                      + Fpa
                      + a2
                      + "|Fca)("
                      + PRP
                      + a2
                      + ")("
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + "|"
                      + CARD
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + "|"
                      + Fpt
                      + a2
                      + "|Fct)",
                  "",
                  true);
          Rel = "CprepR";
          HeadRelDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + NOUNCOORD
                          + "|"
                          + PRO
                          + l
                          + "type:(?:D|P|I|X)\\|"
                          + r
                          + ")("
                          + Fc
                          + a2
                          + "|"
                          + Fpa
                          + a2
                          + "|Fca)("
                          + PRP
                          + a2
                          + ")("
                          + NOUNCOORD
                          + "|"
                          + PRO
                          + l
                          + "type:(?:D|P|I|X)\\|"
                          + r
                          + "|"
                          + CARD
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + "|"
                          + Fpt
                          + a2
                          + "|Fct)",
                      "$1",
                      "",
                      true));
          /* AdjnR: NOUNCOORD|PRO<type:D|P|I|X> [Fc|Fpa|Fca] VERB<mode:P> [X]? [X]? [X]? [X]? [X]? [X]? [X]? [X]? [X]? [X]? [Fc|Fpt|Fct]
          NoUniq*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")(?:"
                      + Fc
                      + a2
                      + "|"
                      + Fpa
                      + a2
                      + "|Fca)("
                      + VERB
                      + l
                      + "mode:P\\|"
                      + r
                      + ")(?:"
                      + X
                      + a2
                      + ")?(?:"
                      + X
                      + a2
                      + ")?(?:"
                      + X
                      + a2
                      + ")?(?:"
                      + X
                      + a2
                      + ")?(?:"
                      + X
                      + a2
                      + ")?(?:"
                      + X
                      + a2
                      + ")?(?:"
                      + X
                      + a2
                      + ")?(?:"
                      + X
                      + a2
                      + ")?(?:"
                      + X
                      + a2
                      + ")?(?:"
                      + X
                      + a2
                      + ")?(?:"
                      + Fc
                      + a2
                      + "|"
                      + Fpt
                      + a2
                      + "|Fct)",
                  "",
                  true);
          Rel = "AdjnR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + NOUNCOORD
                          + "|"
                          + PRO
                          + l
                          + "type:(?:D|P|I|X)\\|"
                          + r
                          + ")("
                          + Fc
                          + a2
                          + "|"
                          + Fpa
                          + a2
                          + "|Fca)("
                          + VERB
                          + l
                          + "mode:P\\|"
                          + r
                          + ")("
                          + X
                          + a2
                          + ")?("
                          + X
                          + a2
                          + ")?("
                          + X
                          + a2
                          + ")?("
                          + X
                          + a2
                          + ")?("
                          + X
                          + a2
                          + ")?("
                          + X
                          + a2
                          + ")?("
                          + X
                          + a2
                          + ")?("
                          + X
                          + a2
                          + ")?("
                          + X
                          + a2
                          + ")?("
                          + X
                          + a2
                          + ")?("
                          + Fc
                          + a2
                          + "|"
                          + Fpt
                          + a2
                          + "|Fct)",
                      "$1$2$3$4$5$6$7$8$9$10$11$12$13$14",
                      "",
                      true));
          /* SubjL: [NOUNCOORD] PRO<type:R|W> VERB|CONJ<coord:verb>
          NEXT
          AdjnR: NOUNCOORD [PRO<type:R|W>] VERB|CONJ<coord:verb>
          NoUniq*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + NOUNCOORD
                      + ")("
                      + PRO
                      + l
                      + "type:(?:R|W)\\|"
                      + r
                      + ")("
                      + VERB
                      + a2
                      + "|"
                      + CONJ
                      + l
                      + "coord:verb\\|"
                      + r
                      + ")",
                  "",
                  true);
          Rel = "SubjL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + NOUNCOORD
                      + ")(?:"
                      + PRO
                      + l
                      + "type:(?:R|W)\\|"
                      + r
                      + ")("
                      + VERB
                      + a2
                      + "|"
                      + CONJ
                      + l
                      + "coord:verb\\|"
                      + r
                      + ")",
                  "",
                  true);
          Rel = "AdjnR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + NOUNCOORD
                          + ")("
                          + PRO
                          + l
                          + "type:(?:R|W)\\|"
                          + r
                          + ")("
                          + VERB
                          + a2
                          + "|"
                          + CONJ
                          + l
                          + "coord:verb\\|"
                          + r
                          + ")",
                      "$1$2$3",
                      "",
                      true));
          /* DobjL: [NOUNCOORD] PRO<type:R|W> [NOUNCOORD|PRO<type:D|P|I|X>] VERB|CONJ<coord:verb>
          NEXT
          AdjnR: NOUNCOORD [PRO<type:R|W>] [NOUNCOORD|PRO<type:D|P|I|X>] VERB|CONJ<coord:verb>
          NoUniq*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + NOUNCOORD
                      + ")("
                      + PRO
                      + l
                      + "type:(?:R|W)\\|"
                      + r
                      + ")(?:"
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")("
                      + VERB
                      + a2
                      + "|"
                      + CONJ
                      + l
                      + "coord:verb\\|"
                      + r
                      + ")",
                  "",
                  true);
          Rel = "DobjL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + NOUNCOORD
                      + ")(?:"
                      + PRO
                      + l
                      + "type:(?:R|W)\\|"
                      + r
                      + ")(?:"
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")("
                      + VERB
                      + a2
                      + "|"
                      + CONJ
                      + l
                      + "coord:verb\\|"
                      + r
                      + ")",
                  "",
                  true);
          Rel = "AdjnR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + NOUNCOORD
                          + ")("
                          + PRO
                          + l
                          + "type:(?:R|W)\\|"
                          + r
                          + ")("
                          + NOUNCOORD
                          + "|"
                          + PRO
                          + l
                          + "type:(?:D|P|I|X)\\|"
                          + r
                          + ")("
                          + VERB
                          + a2
                          + "|"
                          + CONJ
                          + l
                          + "coord:verb\\|"
                          + r
                          + ")",
                      "$1$2$3$4",
                      "",
                      true));
          /* CircL: [NOUNCOORD|PRO<type:D|P|I|X>]  PRP PRO<type:R|W> VERB|CONJ<coord:verb>
          NEXT
          AdjnR: NOUNCOORD|PRO<type:D|P|I|X> [PRP] [PRO<type:R|W>] VERB|CONJ<coord:verb>
          NoUniq*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")("
                      + PRP
                      + a2
                      + ")("
                      + PRO
                      + l
                      + "type:(?:R|W)\\|"
                      + r
                      + ")("
                      + VERB
                      + a2
                      + "|"
                      + CONJ
                      + l
                      + "coord:verb\\|"
                      + r
                      + ")",
                  "",
                  true);
          Rel = "CircL";
          RelDepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + PRO
                      + l
                      + "type:(?:R|W)\\|"
                      + r
                      + ")("
                      + VERB
                      + a2
                      + "|"
                      + CONJ
                      + l
                      + "coord:verb\\|"
                      + r
                      + ")",
                  "",
                  true);
          Rel = "AdjnR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + NOUNCOORD
                          + "|"
                          + PRO
                          + l
                          + "type:(?:D|P|I|X)\\|"
                          + r
                          + ")("
                          + PRP
                          + a2
                          + ")("
                          + PRO
                          + l
                          + "type:(?:R|W)\\|"
                          + r
                          + ")("
                          + VERB
                          + a2
                          + "|"
                          + CONJ
                          + l
                          + "coord:verb\\|"
                          + r
                          + ")",
                      "$1$2$3$4",
                      "",
                      true));
          /* AdjnR: NOUNCOORD|PRO<type:D|P|I|X>  VERB<mode:[GP]>|CONJ<coord:verb>
          NoUniq*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")("
                      + VERB
                      + l
                      + "mode:[GP]\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "coord:verb\\|"
                      + r
                      + ")",
                  "",
                  true);
          Rel = "AdjnR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + NOUNCOORD
                          + "|"
                          + PRO
                          + l
                          + "type:(?:D|P|I|X)\\|"
                          + r
                          + ")("
                          + VERB
                          + l
                          + "mode:[GP]\\|"
                          + r
                          + "|"
                          + CONJ
                          + l
                          + "coord:verb\\|"
                          + r
                          + ")",
                      "$1$2",
                      "",
                      true));
          /* CircR: VERB<lemma:$PTa> [NOUNCOORD|PRO<type:D|P|I|X>] PRP<lemma:a> NOUNCOORD|PRO<type:D|P|I|X>|VERB<mode:N>|ADV*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + VERB
                      + l
                      + "lemma:"
                      + PTa
                      + "\\|"
                      + r
                      + ")(?:"
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")("
                      + PRP
                      + l
                      + "lemma:a\\|"
                      + r
                      + ")("
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + "|"
                      + VERB
                      + l
                      + "mode:N\\|"
                      + r
                      + "|"
                      + ADV
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CircR";
          HeadRelDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + VERB
                          + l
                          + "lemma:"
                          + PTa
                          + "\\|"
                          + r
                          + ")("
                          + NOUNCOORD
                          + "|"
                          + PRO
                          + l
                          + "type:(?:D|P|I|X)\\|"
                          + r
                          + ")("
                          + PRP
                          + l
                          + "lemma:a\\|"
                          + r
                          + ")("
                          + NOUNCOORD
                          + "|"
                          + PRO
                          + l
                          + "type:(?:D|P|I|X)\\|"
                          + r
                          + "|"
                          + VERB
                          + l
                          + "mode:N\\|"
                          + r
                          + "|"
                          + ADV
                          + a2
                          + ")",
                      "$1$2",
                      "",
                      true));
          /* CircR: VERB<mode:P> [NOUNCOORD|PRO<type:D|P|I|X>] PRP<lemma:por|by> NOUNCOORD|PRO<type:D|P|I|X>|ADV*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + VERB
                      + l
                      + "mode:P\\|"
                      + r
                      + ")(?:"
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")("
                      + PRP
                      + l
                      + "lemma:(?:por|by)\\|"
                      + r
                      + ")("
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + "|"
                      + ADV
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CircR";
          HeadRelDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + VERB
                          + l
                          + "mode:P\\|"
                          + r
                          + ")("
                          + NOUNCOORD
                          + "|"
                          + PRO
                          + l
                          + "type:(?:D|P|I|X)\\|"
                          + r
                          + ")("
                          + PRP
                          + l
                          + "lemma:(?:por|by)\\|"
                          + r
                          + ")("
                          + NOUNCOORD
                          + "|"
                          + PRO
                          + l
                          + "type:(?:D|P|I|X)\\|"
                          + r
                          + "|"
                          + ADV
                          + a2
                          + ")",
                      "$1$2",
                      "",
                      true));
          /* CircR: VERB [NOUNCOORD|PRO<type:D|P|I|X>] PRP<lemma:$PrepMA> CARD|DATE*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + VERB
                      + a2
                      + ")(?:"
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")("
                      + PRP
                      + l
                      + "lemma:"
                      + PrepMA
                      + "\\|"
                      + r
                      + ")("
                      + CARD
                      + a2
                      + "|"
                      + DATE
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CircR";
          HeadRelDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + VERB
                          + a2
                          + ")("
                          + NOUNCOORD
                          + "|"
                          + PRO
                          + l
                          + "type:(?:D|P|I|X)\\|"
                          + r
                          + ")("
                          + PRP
                          + l
                          + "lemma:"
                          + PrepMA
                          + "\\|"
                          + r
                          + ")("
                          + CARD
                          + a2
                          + "|"
                          + DATE
                          + a2
                          + ")",
                      "$1$2",
                      "",
                      true));
          /* CprepR: CARD PRP<lemma:de|entre|sobre|of|about|between> NOUNCOORD|PRO<type:D|P|I|X>*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + CARD
                      + a2
                      + ")("
                      + PRP
                      + l
                      + "lemma:(?:de|entre|sobre|of|about|between)\\|"
                      + r
                      + ")("
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")",
                  "",
                  true);
          Rel = "CprepR";
          HeadRelDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + CARD
                          + a2
                          + ")("
                          + PRP
                          + l
                          + "lemma:(?:de|entre|sobre|of|about|between)\\|"
                          + r
                          + ")("
                          + NOUNCOORD
                          + "|"
                          + PRO
                          + l
                          + "type:(?:D|P|I|X)\\|"
                          + r
                          + ")",
                      "$1",
                      "",
                      true));
          /* CprepR: PRO<type:P> PRP<lemma:de|of> NOUNCOORD|PRO<type:D|P|I|X>*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + PRO
                      + l
                      + "type:P\\|"
                      + r
                      + ")("
                      + PRP
                      + l
                      + "lemma:(?:de|of)\\|"
                      + r
                      + ")("
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")",
                  "",
                  true);
          Rel = "CprepR";
          HeadRelDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + PRO
                          + l
                          + "type:P\\|"
                          + r
                          + ")("
                          + PRP
                          + l
                          + "lemma:(?:de|of)\\|"
                          + r
                          + ")("
                          + NOUNCOORD
                          + "|"
                          + PRO
                          + l
                          + "type:(?:D|P|I|X)\\|"
                          + r
                          + ")",
                      "$1",
                      "",
                      true));
          /* CprepR: NOUNCOORD [PRP] [PRO<type:D|P|I|X>] PRP NOUNCOORD|ADV
          NEXT
          CprepR: NOUNCOORD PRP PRO<type:D|P|I|X> [PRP] [NOUNCOORD]|ADV*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + NOUNCOORD
                      + ")(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")("
                      + PRP
                      + a2
                      + ")("
                      + NOUNCOORD
                      + "|"
                      + ADV
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CprepR";
          HeadRelDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + NOUNCOORD
                      + ")("
                      + PRP
                      + a2
                      + ")("
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")(?:"
                      + PRP
                      + a2
                      + ")([NOUNCOORD]|ADV)",
                  "",
                  true);
          Rel = "CprepR";
          HeadRelDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + NOUNCOORD
                          + ")("
                          + PRP
                          + a2
                          + ")("
                          + PRO
                          + l
                          + "type:(?:D|P|I|X)\\|"
                          + r
                          + ")("
                          + PRP
                          + a2
                          + ")([NOUNCOORD]|ADV)",
                      "$1",
                      "",
                      true));
          /* PrepR: VERB [NOUNCOORD|PRO<type:D|P|I|X>] CONJ<coord:cprep>*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + VERB
                      + a2
                      + ")(?:"
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")("
                      + CONJ
                      + l
                      + "coord:cprep\\|"
                      + r
                      + ")",
                  "",
                  true);
          Rel = "PrepR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + VERB
                          + a2
                          + ")("
                          + NOUNCOORD
                          + "|"
                          + PRO
                          + l
                          + "type:(?:D|P|I|X)\\|"
                          + r
                          + ")("
                          + CONJ
                          + l
                          + "coord:cprep\\|"
                          + r
                          + ")",
                      "$1$2",
                      "",
                      true));
          /* PrepR: NOUNCOORD|PRO<type:D|P|I|X> CONJ<coord:cprep>*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")("
                      + CONJ
                      + l
                      + "coord:cprep\\|"
                      + r
                      + ")",
                  "",
                  true);
          Rel = "PrepR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + NOUNCOORD
                          + "|"
                          + PRO
                          + l
                          + "type:(?:D|P|I|X)\\|"
                          + r
                          + ")("
                          + CONJ
                          + l
                          + "coord:cprep\\|"
                          + r
                          + ")",
                      "$1",
                      "",
                      true));
          /* DobjR: VERB NOUNCOORD|PRO*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(" + VERB + a2 + ")(" + NOUNCOORD + "|" + PRO + a2 + ")",
                  "",
                  true);
          Rel = "DobjR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + VERB + a2 + ")(" + NOUNCOORD + "|" + PRO + a2 + ")",
                      "$1",
                      "",
                      true));
          /* AdjnR: VERB ADJ|CONJ<coord:adj>*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(" + VERB + a2 + ")(" + ADJ + a2 + "|" + CONJ + l + "coord:adj\\|" + r + ")",
                  "",
                  true);
          Rel = "AdjnR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + VERB + a2 + ")(" + ADJ + a2 + "|" + CONJ + l + "coord:adj\\|" + r + ")",
                      "$1",
                      "",
                      true));
          /* CoordL: VERB CONJ<(type:C)|(lemma:and|or|y|e|et|o|ou)> [VERB]
          NEXT
          CoordR: [VERB]  CONJ<(type:C)|(lemma:and|or|y|e|et|o|ou)> VERB
          Add: coord:verb
          Inherit: mode, tense*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + VERB
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + VERB
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + VERB
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")("
                      + VERB
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + VERB
                          + a2
                          + ")("
                          + CONJ
                          + l
                          + "type:C\\|"
                          + r
                          + "|"
                          + CONJ
                          + l
                          + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                          + r
                          + ")("
                          + VERB
                          + a2
                          + ")",
                      "$2",
                      "",
                      true));
          Inherit(
              Pd.union()
                  .append(new Box[] {Casting.box("HeadDep"), Casting.box("mode,tense")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          Add(
              Pd.union()
                  .append(new Box[] {Casting.box("HeadDep"), Casting.box("coord:verb")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          /* CoordL: VERB [Fc] [VERB] [Fc] CONJ<(type:C)|(lemma:and|or|y|e|et|o|ou)> [VERB]
          NEXT
          PunctL:  [VERB] Fc [VERB] [Fc] CONJ<(type:C)|(lemma:and|or|y|e|et|o|ou)> [VERB]
          Recursivity: 5*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + VERB
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")(?:"
                      + VERB
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + VERB
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + VERB
                      + a2
                      + ")("
                      + Fc
                      + a2
                      + ")(?:"
                      + VERB
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + VERB
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + VERB
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + VERB
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + CONJ
                          + l
                          + "type:C\\|"
                          + r
                          + "|"
                          + CONJ
                          + l
                          + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                          + r
                          + ")("
                          + VERB
                          + a2
                          + ")",
                      "$3$4$5$6",
                      "",
                      true));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + VERB
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")(?:"
                      + VERB
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + VERB
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + VERB
                      + a2
                      + ")("
                      + Fc
                      + a2
                      + ")(?:"
                      + VERB
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + VERB
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + VERB
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + VERB
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + CONJ
                          + l
                          + "type:C\\|"
                          + r
                          + "|"
                          + CONJ
                          + l
                          + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                          + r
                          + ")("
                          + VERB
                          + a2
                          + ")",
                      "$3$4$5$6",
                      "",
                      true));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + VERB
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")(?:"
                      + VERB
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + VERB
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + VERB
                      + a2
                      + ")("
                      + Fc
                      + a2
                      + ")(?:"
                      + VERB
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + VERB
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + VERB
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + VERB
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + CONJ
                          + l
                          + "type:C\\|"
                          + r
                          + "|"
                          + CONJ
                          + l
                          + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                          + r
                          + ")("
                          + VERB
                          + a2
                          + ")",
                      "$3$4$5$6",
                      "",
                      true));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + VERB
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")(?:"
                      + VERB
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + VERB
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + VERB
                      + a2
                      + ")("
                      + Fc
                      + a2
                      + ")(?:"
                      + VERB
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + VERB
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + VERB
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + VERB
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + CONJ
                          + l
                          + "type:C\\|"
                          + r
                          + "|"
                          + CONJ
                          + l
                          + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                          + r
                          + ")("
                          + VERB
                          + a2
                          + ")",
                      "$3$4$5$6",
                      "",
                      true));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + VERB
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")(?:"
                      + VERB
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + VERB
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + VERB
                      + a2
                      + ")("
                      + Fc
                      + a2
                      + ")(?:"
                      + VERB
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + VERB
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + VERB
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + VERB
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + CONJ
                          + l
                          + "type:C\\|"
                          + r
                          + "|"
                          + CONJ
                          + l
                          + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                          + r
                          + ")("
                          + VERB
                          + a2
                          + ")",
                      "$3$4$5$6",
                      "",
                      true));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + VERB
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")(?:"
                      + VERB
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + VERB
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + VERB
                      + a2
                      + ")("
                      + Fc
                      + a2
                      + ")(?:"
                      + VERB
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + VERB
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + VERB
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + VERB
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + CONJ
                          + l
                          + "type:C\\|"
                          + r
                          + "|"
                          + CONJ
                          + l
                          + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                          + r
                          + ")("
                          + VERB
                          + a2
                          + ")",
                      "$3$4$5$6",
                      "",
                      true));
          /* PunctL: [VERB] Fc CONJ<(type:C)|(lemma:and|or|y|e|et|o|ou)> [VERB]
          NEXT
          CoordL: VERB [Fc] CONJ<(type:C)|(lemma:and|or|y|e|et|o|ou)> [VERB]
          NEXT
          CoordR:  [VERB] [Fc] CONJ<(type:C)|(lemma:and|or|y|e|et|o|ou)> VERB
          Add: coord:verb
          Inherit: mode, tense*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + VERB
                      + a2
                      + ")("
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + VERB
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + VERB
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + VERB
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + VERB
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")("
                      + VERB
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + VERB
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + CONJ
                          + l
                          + "type:C\\|"
                          + r
                          + "|"
                          + CONJ
                          + l
                          + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                          + r
                          + ")("
                          + VERB
                          + a2
                          + ")",
                      "$3",
                      "",
                      true));
          Inherit(
              Pd.union()
                  .append(new Box[] {Casting.box("HeadDep"), Casting.box("mode,tense")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          Add(
              Pd.union()
                  .append(new Box[] {Casting.box("HeadDep"), Casting.box("coord:verb")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          /* CircR: [VERB|CONJ<coord:verb>] [PRP] VERB|CONJ<coord:verb> [PRP] [CARD|NOUNCOORD|PRO<type:D|P|I|X>] PRP CARD|NOUNCOORD|PRO<type:D|P|I|X>
          Recursivity: 1*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + VERB
                      + a2
                      + "|"
                      + CONJ
                      + l
                      + "coord:verb\\|"
                      + r
                      + ")(?:"
                      + PRP
                      + a2
                      + ")("
                      + VERB
                      + a2
                      + "|"
                      + CONJ
                      + l
                      + "coord:verb\\|"
                      + r
                      + ")(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + CARD
                      + a2
                      + "|"
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")("
                      + PRP
                      + a2
                      + ")("
                      + CARD
                      + a2
                      + "|"
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")",
                  "",
                  true);
          Rel = "CircR";
          HeadRelDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + VERB
                          + a2
                          + "|"
                          + CONJ
                          + l
                          + "coord:verb\\|"
                          + r
                          + ")("
                          + PRP
                          + a2
                          + ")("
                          + VERB
                          + a2
                          + "|"
                          + CONJ
                          + l
                          + "coord:verb\\|"
                          + r
                          + ")("
                          + PRP
                          + a2
                          + ")("
                          + CARD
                          + a2
                          + "|"
                          + NOUNCOORD
                          + "|"
                          + PRO
                          + l
                          + "type:(?:D|P|I|X)\\|"
                          + r
                          + ")("
                          + PRP
                          + a2
                          + ")("
                          + CARD
                          + a2
                          + "|"
                          + NOUNCOORD
                          + "|"
                          + PRO
                          + l
                          + "type:(?:D|P|I|X)\\|"
                          + r
                          + ")",
                      "$1$2$3$4$5",
                      "",
                      true));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + VERB
                      + a2
                      + "|"
                      + CONJ
                      + l
                      + "coord:verb\\|"
                      + r
                      + ")(?:"
                      + PRP
                      + a2
                      + ")("
                      + VERB
                      + a2
                      + "|"
                      + CONJ
                      + l
                      + "coord:verb\\|"
                      + r
                      + ")(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + CARD
                      + a2
                      + "|"
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")("
                      + PRP
                      + a2
                      + ")("
                      + CARD
                      + a2
                      + "|"
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")",
                  "",
                  true);
          Rel = "CircR";
          HeadRelDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + VERB
                          + a2
                          + "|"
                          + CONJ
                          + l
                          + "coord:verb\\|"
                          + r
                          + ")("
                          + PRP
                          + a2
                          + ")("
                          + VERB
                          + a2
                          + "|"
                          + CONJ
                          + l
                          + "coord:verb\\|"
                          + r
                          + ")("
                          + PRP
                          + a2
                          + ")("
                          + CARD
                          + a2
                          + "|"
                          + NOUNCOORD
                          + "|"
                          + PRO
                          + l
                          + "type:(?:D|P|I|X)\\|"
                          + r
                          + ")("
                          + PRP
                          + a2
                          + ")("
                          + CARD
                          + a2
                          + "|"
                          + NOUNCOORD
                          + "|"
                          + PRO
                          + l
                          + "type:(?:D|P|I|X)\\|"
                          + r
                          + ")",
                      "$1$2$3$4$5",
                      "",
                      true));
          /* CircR: [VERB|CONJ<coord:verb>] [PRP] VERB|CONJ<coord:verb> PRP CARD|NOUNCOORD|PRO<type:D|P|I|X>
          NEXT
          CircR: VERB|CONJ<coord:verb> PRP VERB|CONJ<coord:verb> [PRP] [CARD|NOUNCOORD|PRO<type:D|P|I|X>]*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + VERB
                      + a2
                      + "|"
                      + CONJ
                      + l
                      + "coord:verb\\|"
                      + r
                      + ")(?:"
                      + PRP
                      + a2
                      + ")("
                      + VERB
                      + a2
                      + "|"
                      + CONJ
                      + l
                      + "coord:verb\\|"
                      + r
                      + ")("
                      + PRP
                      + a2
                      + ")("
                      + CARD
                      + a2
                      + "|"
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")",
                  "",
                  true);
          Rel = "CircR";
          HeadRelDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + VERB
                      + a2
                      + "|"
                      + CONJ
                      + l
                      + "coord:verb\\|"
                      + r
                      + ")("
                      + PRP
                      + a2
                      + ")("
                      + VERB
                      + a2
                      + "|"
                      + CONJ
                      + l
                      + "coord:verb\\|"
                      + r
                      + ")(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + CARD
                      + a2
                      + "|"
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")",
                  "",
                  true);
          Rel = "CircR";
          HeadRelDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + VERB
                          + a2
                          + "|"
                          + CONJ
                          + l
                          + "coord:verb\\|"
                          + r
                          + ")("
                          + PRP
                          + a2
                          + ")("
                          + VERB
                          + a2
                          + "|"
                          + CONJ
                          + l
                          + "coord:verb\\|"
                          + r
                          + ")("
                          + PRP
                          + a2
                          + ")("
                          + CARD
                          + a2
                          + "|"
                          + NOUNCOORD
                          + "|"
                          + PRO
                          + l
                          + "type:(?:D|P|I|X)\\|"
                          + r
                          + ")",
                      "$1",
                      "",
                      true));
          /* CircR: [VERB|CONJ<coord:verb>] [PRP] VERB|CONJ<coord:verb> PRP CARD|NOUNCOORD|PRO<type:D|P|I|X>
          NEXT
          CircR: VERB|CONJ<coord:verb> PRP VERB|CONJ<coord:verb> [PRP] [CARD|NOUNCOORD|PRO<type:D|P|I|X>]*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + VERB
                      + a2
                      + "|"
                      + CONJ
                      + l
                      + "coord:verb\\|"
                      + r
                      + ")(?:"
                      + PRP
                      + a2
                      + ")("
                      + VERB
                      + a2
                      + "|"
                      + CONJ
                      + l
                      + "coord:verb\\|"
                      + r
                      + ")("
                      + PRP
                      + a2
                      + ")("
                      + CARD
                      + a2
                      + "|"
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")",
                  "",
                  true);
          Rel = "CircR";
          HeadRelDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + VERB
                      + a2
                      + "|"
                      + CONJ
                      + l
                      + "coord:verb\\|"
                      + r
                      + ")("
                      + PRP
                      + a2
                      + ")("
                      + VERB
                      + a2
                      + "|"
                      + CONJ
                      + l
                      + "coord:verb\\|"
                      + r
                      + ")(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + CARD
                      + a2
                      + "|"
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")",
                  "",
                  true);
          Rel = "CircR";
          HeadRelDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + VERB
                          + a2
                          + "|"
                          + CONJ
                          + l
                          + "coord:verb\\|"
                          + r
                          + ")("
                          + PRP
                          + a2
                          + ")("
                          + VERB
                          + a2
                          + "|"
                          + CONJ
                          + l
                          + "coord:verb\\|"
                          + r
                          + ")("
                          + PRP
                          + a2
                          + ")("
                          + CARD
                          + a2
                          + "|"
                          + NOUNCOORD
                          + "|"
                          + PRO
                          + l
                          + "type:(?:D|P|I|X)\\|"
                          + r
                          + ")",
                      "$1",
                      "",
                      true));
          /* CircR: VERB|CONJ<coord:verb> PRP VERB|ADV|CARD
          Recursivity: 1*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + VERB
                      + a2
                      + "|"
                      + CONJ
                      + l
                      + "coord:verb\\|"
                      + r
                      + ")("
                      + PRP
                      + a2
                      + ")("
                      + VERB
                      + a2
                      + "|"
                      + ADV
                      + a2
                      + "|CARD)",
                  "",
                  true);
          Rel = "CircR";
          HeadRelDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + VERB
                          + a2
                          + "|"
                          + CONJ
                          + l
                          + "coord:verb\\|"
                          + r
                          + ")("
                          + PRP
                          + a2
                          + ")("
                          + VERB
                          + a2
                          + "|"
                          + ADV
                          + a2
                          + "|CARD)",
                      "$1",
                      "",
                      true));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + VERB
                      + a2
                      + "|"
                      + CONJ
                      + l
                      + "coord:verb\\|"
                      + r
                      + ")("
                      + PRP
                      + a2
                      + ")("
                      + VERB
                      + a2
                      + "|"
                      + ADV
                      + a2
                      + "|CARD)",
                  "",
                  true);
          Rel = "CircR";
          HeadRelDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + VERB
                          + a2
                          + "|"
                          + CONJ
                          + l
                          + "coord:verb\\|"
                          + r
                          + ")("
                          + PRP
                          + a2
                          + ")("
                          + VERB
                          + a2
                          + "|"
                          + ADV
                          + a2
                          + "|CARD)",
                      "$1",
                      "",
                      true));
          /* CircR: VERB|CONJ<coord:verb> PRP NOUNCOORD|PRO<type:D|P|I|X>
          Recursivity: 1*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + VERB
                      + a2
                      + "|"
                      + CONJ
                      + l
                      + "coord:verb\\|"
                      + r
                      + ")("
                      + PRP
                      + a2
                      + ")("
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")",
                  "",
                  true);
          Rel = "CircR";
          HeadRelDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + VERB
                          + a2
                          + "|"
                          + CONJ
                          + l
                          + "coord:verb\\|"
                          + r
                          + ")("
                          + PRP
                          + a2
                          + ")("
                          + NOUNCOORD
                          + "|"
                          + PRO
                          + l
                          + "type:(?:D|P|I|X)\\|"
                          + r
                          + ")",
                      "$1",
                      "",
                      true));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + VERB
                      + a2
                      + "|"
                      + CONJ
                      + l
                      + "coord:verb\\|"
                      + r
                      + ")("
                      + PRP
                      + a2
                      + ")("
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")",
                  "",
                  true);
          Rel = "CircR";
          HeadRelDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + VERB
                          + a2
                          + "|"
                          + CONJ
                          + l
                          + "coord:verb\\|"
                          + r
                          + ")("
                          + PRP
                          + a2
                          + ")("
                          + NOUNCOORD
                          + "|"
                          + PRO
                          + l
                          + "type:(?:D|P|I|X)\\|"
                          + r
                          + ")",
                      "$1",
                      "",
                      true));
          /* CircR: VERB|CONJ<coord:verb> [X]? [X]? [X]? [X]? [X]? [X]? [X]? [X]? [X]? [X]? PRP<lemma:to> VERB|CONJ<coord:verb>*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + VERB
                      + a2
                      + "|"
                      + CONJ
                      + l
                      + "coord:verb\\|"
                      + r
                      + ")(?:"
                      + X
                      + a2
                      + ")?(?:"
                      + X
                      + a2
                      + ")?(?:"
                      + X
                      + a2
                      + ")?(?:"
                      + X
                      + a2
                      + ")?(?:"
                      + X
                      + a2
                      + ")?(?:"
                      + X
                      + a2
                      + ")?(?:"
                      + X
                      + a2
                      + ")?(?:"
                      + X
                      + a2
                      + ")?(?:"
                      + X
                      + a2
                      + ")?(?:"
                      + X
                      + a2
                      + ")?("
                      + PRP
                      + l
                      + "lemma:to\\|"
                      + r
                      + ")("
                      + VERB
                      + a2
                      + "|"
                      + CONJ
                      + l
                      + "coord:verb\\|"
                      + r
                      + ")",
                  "",
                  true);
          Rel = "CircR";
          HeadRelDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + VERB
                          + a2
                          + "|"
                          + CONJ
                          + l
                          + "coord:verb\\|"
                          + r
                          + ")("
                          + X
                          + a2
                          + ")?("
                          + X
                          + a2
                          + ")?("
                          + X
                          + a2
                          + ")?("
                          + X
                          + a2
                          + ")?("
                          + X
                          + a2
                          + ")?("
                          + X
                          + a2
                          + ")?("
                          + X
                          + a2
                          + ")?("
                          + X
                          + a2
                          + ")?("
                          + X
                          + a2
                          + ")?("
                          + X
                          + a2
                          + ")?("
                          + PRP
                          + l
                          + "lemma:to\\|"
                          + r
                          + ")("
                          + VERB
                          + a2
                          + "|"
                          + CONJ
                          + l
                          + "coord:verb\\|"
                          + r
                          + ")",
                      "$1$2$3$4$5$6$7$8$9$10$11",
                      "",
                      true));
          /* PunctR: VERB Fc [PRP] [NOUNCOORD|PRO<type:D|P|I|X>] [Fc]?
          NEXT
          PunctR: VERB [Fc] [PRP] [NOUNCOORD|PRO<type:D|P|I|X>] Fc
          NEXT
          TermR: [VERB] [Fc] PRP NOUNCOORD|PRO<type:D|P|I|X> [Fc]?
          NEXT
          CircR: VERB [Fc] PRP [NOUNCOORD|PRO<type:D|P|I|X>] [Fc]?
          Recursivity:2*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + VERB
                      + a2
                      + ")("
                      + Fc
                      + a2
                      + ")(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")(?:"
                      + Fc
                      + a2
                      + ")?",
                  "",
                  true);
          Rel = "PunctR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + VERB
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")("
                      + Fc
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + VERB
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + PRP
                      + a2
                      + ")("
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")(?:"
                      + Fc
                      + a2
                      + ")?",
                  "",
                  true);
          Rel = "TermR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + VERB
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")(?:"
                      + Fc
                      + a2
                      + ")?",
                  "",
                  true);
          Rel = "CircR";
          HeadRelDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + VERB
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + PRP
                          + a2
                          + ")("
                          + NOUNCOORD
                          + "|"
                          + PRO
                          + l
                          + "type:(?:D|P|I|X)\\|"
                          + r
                          + ")("
                          + Fc
                          + a2
                          + ")?",
                      "$1",
                      "",
                      true));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + VERB
                      + a2
                      + ")("
                      + Fc
                      + a2
                      + ")(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")(?:"
                      + Fc
                      + a2
                      + ")?",
                  "",
                  true);
          Rel = "PunctR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + VERB
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")("
                      + Fc
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + VERB
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + PRP
                      + a2
                      + ")("
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")(?:"
                      + Fc
                      + a2
                      + ")?",
                  "",
                  true);
          Rel = "TermR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + VERB
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")(?:"
                      + Fc
                      + a2
                      + ")?",
                  "",
                  true);
          Rel = "CircR";
          HeadRelDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + VERB
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + PRP
                          + a2
                          + ")("
                          + NOUNCOORD
                          + "|"
                          + PRO
                          + l
                          + "type:(?:D|P|I|X)\\|"
                          + r
                          + ")("
                          + Fc
                          + a2
                          + ")?",
                      "$1",
                      "",
                      true));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + VERB
                      + a2
                      + ")("
                      + Fc
                      + a2
                      + ")(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")(?:"
                      + Fc
                      + a2
                      + ")?",
                  "",
                  true);
          Rel = "PunctR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + VERB
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")("
                      + Fc
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + VERB
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + PRP
                      + a2
                      + ")("
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")(?:"
                      + Fc
                      + a2
                      + ")?",
                  "",
                  true);
          Rel = "TermR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + VERB
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")(?:"
                      + Fc
                      + a2
                      + ")?",
                  "",
                  true);
          Rel = "CircR";
          HeadRelDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + VERB
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + PRP
                          + a2
                          + ")("
                          + NOUNCOORD
                          + "|"
                          + PRO
                          + l
                          + "type:(?:D|P|I|X)\\|"
                          + r
                          + ")("
                          + Fc
                          + a2
                          + ")?",
                      "$1",
                      "",
                      true));
          /* PunctL: [PRP<pos:0>] [NOUNCOORD|PRO<type:D|P|I|X>] Fc  VERB
          NEXT
          CircL: PRP<pos:0> NOUNCOORD|PRO<type:D|P|I|X> [Fc]?  VERB*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + PRP
                      + l
                      + "pos:0\\|"
                      + r
                      + ")(?:"
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")("
                      + Fc
                      + a2
                      + ")("
                      + VERB
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + PRP
                      + l
                      + "pos:0\\|"
                      + r
                      + ")("
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")(?:"
                      + Fc
                      + a2
                      + ")?("
                      + VERB
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CircL";
          RelDepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + PRP
                          + l
                          + "pos:0\\|"
                          + r
                          + ")("
                          + NOUNCOORD
                          + "|"
                          + PRO
                          + l
                          + "type:(?:D|P|I|X)\\|"
                          + r
                          + ")("
                          + Fc
                          + a2
                          + ")?("
                          + VERB
                          + a2
                          + ")",
                      "$4",
                      "",
                      true));
          /* PunctL: Fc [PRP] [NOUNCOORD|PRO<type:D|P|I|X>] [Fc]  VERB
          NEXT
          PunctL: [Fc] [PRP] [NOUNCOORD|PRO<type:D|P|I|X>] Fc  VERB
          NEXT
          CircL: [Fc] PRP NOUNCOORD|PRO<type:D|P|I|X> [Fc]  VERB*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + Fc
                      + a2
                      + ")(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + VERB
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + Fc
                      + a2
                      + ")(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")("
                      + Fc
                      + a2
                      + ")("
                      + VERB
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + Fc
                      + a2
                      + ")("
                      + PRP
                      + a2
                      + ")("
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + VERB
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CircL";
          RelDepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + Fc
                          + a2
                          + ")("
                          + PRP
                          + a2
                          + ")("
                          + NOUNCOORD
                          + "|"
                          + PRO
                          + l
                          + "type:(?:D|P|I|X)\\|"
                          + r
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + VERB
                          + a2
                          + ")",
                      "$5",
                      "",
                      true));
          /* AdjnR:  VERB<mode:[^PNG]> DATE*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(" + VERB + l + "mode:[^PNG]\\|" + r + ")(" + DATE + a2 + ")",
                  "",
                  true);
          Rel = "AdjnR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + VERB + l + "mode:[^PNG]\\|" + r + ")(" + DATE + a2 + ")",
                      "$1",
                      "",
                      true));
          /* PunctL: Fc [DATE] VERB<mode:[^PNG]>
          NEXT
          AdjnL:  [Fc]? DATE VERB<mode:[^PNG]>*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(" + Fc + a2 + ")(?:" + DATE + a2 + ")(" + VERB + l + "mode:[^PNG]\\|" + r + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + Fc
                      + a2
                      + ")?("
                      + DATE
                      + a2
                      + ")("
                      + VERB
                      + l
                      + "mode:[^PNG]\\|"
                      + r
                      + ")",
                  "",
                  true);
          Rel = "AdjnL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + Fc
                          + a2
                          + ")?("
                          + DATE
                          + a2
                          + ")("
                          + VERB
                          + l
                          + "mode:[^PNG]\\|"
                          + r
                          + ")",
                      "$3",
                      "",
                      true));
          /* CprepR: NOUNCOORD PRP NOUNCOORD*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(" + NOUNCOORD + ")(" + PRP + a2 + ")(" + NOUNCOORD + ")",
                  "",
                  true);
          Rel = "CprepR";
          HeadRelDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + NOUNCOORD + ")(" + PRP + a2 + ")(" + NOUNCOORD + ")",
                      "$1",
                      "",
                      true));
          /* SpecL: [VERB] CONJ<lemma:that>  VERB<mode:[^PNG]>
          NEXT
          DobjR: VERB [CONJ<lemma:that>] VERB<mode:[^PNG]>*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + VERB
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "lemma:that\\|"
                      + r
                      + ")("
                      + VERB
                      + l
                      + "mode:[^PNG]\\|"
                      + r
                      + ")",
                  "",
                  true);
          Rel = "SpecL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + VERB
                      + a2
                      + ")(?:"
                      + CONJ
                      + l
                      + "lemma:that\\|"
                      + r
                      + ")("
                      + VERB
                      + l
                      + "mode:[^PNG]\\|"
                      + r
                      + ")",
                  "",
                  true);
          Rel = "DobjR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + VERB
                          + a2
                          + ")("
                          + CONJ
                          + l
                          + "lemma:that\\|"
                          + r
                          + ")("
                          + VERB
                          + l
                          + "mode:[^PNG]\\|"
                          + r
                          + ")",
                      "$1",
                      "",
                      true));
          /* SpecL: [VERB]  CONJ<lemma:that>  [NOUNCOORD|PRO<type:D|P|I|X>] VERB<mode:[^PNG]>
          NEXT
          SubjL:  [VERB]  [CONJ<lemma:that>]  NOUNCOORD|PRO<type:D|P|I|X> VERB<mode:[^PNG]>
          NEXT
          DobjR: VERB   [CONJ<lemma:that>] [NOUNCOORD|PRO<type:D|P|I|X>] VERB<mode:[^PNG]>*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + VERB
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "lemma:that\\|"
                      + r
                      + ")(?:"
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")("
                      + VERB
                      + l
                      + "mode:[^PNG]\\|"
                      + r
                      + ")",
                  "",
                  true);
          Rel = "SpecL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + VERB
                      + a2
                      + ")(?:"
                      + CONJ
                      + l
                      + "lemma:that\\|"
                      + r
                      + ")("
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")("
                      + VERB
                      + l
                      + "mode:[^PNG]\\|"
                      + r
                      + ")",
                  "",
                  true);
          Rel = "SubjL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + VERB
                      + a2
                      + ")(?:"
                      + CONJ
                      + l
                      + "lemma:that\\|"
                      + r
                      + ")(?:"
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")("
                      + VERB
                      + l
                      + "mode:[^PNG]\\|"
                      + r
                      + ")",
                  "",
                  true);
          Rel = "DobjR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + VERB
                          + a2
                          + ")("
                          + CONJ
                          + l
                          + "lemma:that\\|"
                          + r
                          + ")("
                          + NOUNCOORD
                          + "|"
                          + PRO
                          + l
                          + "type:(?:D|P|I|X)\\|"
                          + r
                          + ")("
                          + VERB
                          + l
                          + "mode:[^PNG]\\|"
                          + r
                          + ")",
                      "$1",
                      "",
                      true));
          /* PunctL: [NOUNCOORD|PRO<type:D|P|I|X>] Fc|Fpa VERB [Fc|Fpt]
          NEXT
          PunctR: [NOUNCOORD|PRO<type:D|P|I|X>] [Fc|Fpa] VERB Fc|Fpt
          NEXT
          AdjnR: NOUNCOORD|PRO<type:D|P|I|X> [Fc|Fpa] VERB [Fc|Fpt]*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")("
                      + Fc
                      + a2
                      + "|"
                      + Fpa
                      + a2
                      + ")("
                      + VERB
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + "|"
                      + Fpt
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")(?:"
                      + Fc
                      + a2
                      + "|"
                      + Fpa
                      + a2
                      + ")("
                      + VERB
                      + a2
                      + ")("
                      + Fc
                      + a2
                      + "|"
                      + Fpt
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")(?:"
                      + Fc
                      + a2
                      + "|"
                      + Fpa
                      + a2
                      + ")("
                      + VERB
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + "|"
                      + Fpt
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "AdjnR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + NOUNCOORD
                          + "|"
                          + PRO
                          + l
                          + "type:(?:D|P|I|X)\\|"
                          + r
                          + ")("
                          + Fc
                          + a2
                          + "|"
                          + Fpa
                          + a2
                          + ")("
                          + VERB
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + "|"
                          + Fpt
                          + a2
                          + ")",
                      "$1",
                      "",
                      true));
          /* AdjnL: [Fc] VERB<mode:P> [Fc] VERB
          NEXT
          PunctL: Fc [VERB<mode:P>] [Fc] VERB
          NEXT
          PunctL: [Fc] [VERB<mode:P>] Fc VERB*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + Fc
                      + a2
                      + ")("
                      + VERB
                      + l
                      + "mode:P\\|"
                      + r
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + VERB
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "AdjnL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + Fc
                      + a2
                      + ")(?:"
                      + VERB
                      + l
                      + "mode:P\\|"
                      + r
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + VERB
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + Fc
                      + a2
                      + ")(?:"
                      + VERB
                      + l
                      + "mode:P\\|"
                      + r
                      + ")("
                      + Fc
                      + a2
                      + ")("
                      + VERB
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + Fc
                          + a2
                          + ")("
                          + VERB
                          + l
                          + "mode:P\\|"
                          + r
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + VERB
                          + a2
                          + ")",
                      "$4",
                      "",
                      true));
          /* CoordL: VERB CONJ<(type:C)|(lemma:and|or|y|e|et|o|ou)> [VERB]
          NEXT
          CoordR: [VERB]  CONJ<(type:C)|(lemma:and|or|y|e|et|o|ou)> VERB
          Add: coord:verb
          Inherit: mode, tense*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + VERB
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + VERB
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + VERB
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")("
                      + VERB
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + VERB
                          + a2
                          + ")("
                          + CONJ
                          + l
                          + "type:C\\|"
                          + r
                          + "|"
                          + CONJ
                          + l
                          + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                          + r
                          + ")("
                          + VERB
                          + a2
                          + ")",
                      "$2",
                      "",
                      true));
          Inherit(
              Pd.union()
                  .append(new Box[] {Casting.box("HeadDep"), Casting.box("mode,tense")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          Add(
              Pd.union()
                  .append(new Box[] {Casting.box("HeadDep"), Casting.box("coord:verb")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          /* CoordL: VERB [Fc] [VERB] [Fc] CONJ<(type:C)|(lemma:and|or|y|e|et|o|ou)> [VERB]
          NEXT
          PunctL:  [VERB] Fc [VERB] [Fc] CONJ<(type:C)|(lemma:and|or|y|e|et|o|ou)> [VERB]
          Recursivity: 5*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + VERB
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")(?:"
                      + VERB
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + VERB
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + VERB
                      + a2
                      + ")("
                      + Fc
                      + a2
                      + ")(?:"
                      + VERB
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + VERB
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + VERB
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + VERB
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + CONJ
                          + l
                          + "type:C\\|"
                          + r
                          + "|"
                          + CONJ
                          + l
                          + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                          + r
                          + ")("
                          + VERB
                          + a2
                          + ")",
                      "$3$4$5$6",
                      "",
                      true));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + VERB
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")(?:"
                      + VERB
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + VERB
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + VERB
                      + a2
                      + ")("
                      + Fc
                      + a2
                      + ")(?:"
                      + VERB
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + VERB
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + VERB
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + VERB
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + CONJ
                          + l
                          + "type:C\\|"
                          + r
                          + "|"
                          + CONJ
                          + l
                          + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                          + r
                          + ")("
                          + VERB
                          + a2
                          + ")",
                      "$3$4$5$6",
                      "",
                      true));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + VERB
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")(?:"
                      + VERB
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + VERB
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + VERB
                      + a2
                      + ")("
                      + Fc
                      + a2
                      + ")(?:"
                      + VERB
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + VERB
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + VERB
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + VERB
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + CONJ
                          + l
                          + "type:C\\|"
                          + r
                          + "|"
                          + CONJ
                          + l
                          + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                          + r
                          + ")("
                          + VERB
                          + a2
                          + ")",
                      "$3$4$5$6",
                      "",
                      true));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + VERB
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")(?:"
                      + VERB
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + VERB
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + VERB
                      + a2
                      + ")("
                      + Fc
                      + a2
                      + ")(?:"
                      + VERB
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + VERB
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + VERB
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + VERB
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + CONJ
                          + l
                          + "type:C\\|"
                          + r
                          + "|"
                          + CONJ
                          + l
                          + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                          + r
                          + ")("
                          + VERB
                          + a2
                          + ")",
                      "$3$4$5$6",
                      "",
                      true));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + VERB
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")(?:"
                      + VERB
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + VERB
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + VERB
                      + a2
                      + ")("
                      + Fc
                      + a2
                      + ")(?:"
                      + VERB
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + VERB
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + VERB
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + VERB
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + CONJ
                          + l
                          + "type:C\\|"
                          + r
                          + "|"
                          + CONJ
                          + l
                          + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                          + r
                          + ")("
                          + VERB
                          + a2
                          + ")",
                      "$3$4$5$6",
                      "",
                      true));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + VERB
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")(?:"
                      + VERB
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + VERB
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + VERB
                      + a2
                      + ")("
                      + Fc
                      + a2
                      + ")(?:"
                      + VERB
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + VERB
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + VERB
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + VERB
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + CONJ
                          + l
                          + "type:C\\|"
                          + r
                          + "|"
                          + CONJ
                          + l
                          + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                          + r
                          + ")("
                          + VERB
                          + a2
                          + ")",
                      "$3$4$5$6",
                      "",
                      true));
          /* PunctL: [VERB] Fc CONJ<(type:C)|(lemma:and|or|y|e|et|o|ou)> [VERB]
          NEXT
          CoordL: VERB [Fc] CONJ<(type:C)|(lemma:and|or|y|e|et|o|ou)> [VERB]
          NEXT
          CoordR:  [VERB] [Fc] CONJ<(type:C)|(lemma:and|or|y|e|et|o|ou)> VERB
          Add: coord:verb
          Inherit: mode, tense*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + VERB
                      + a2
                      + ")("
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + VERB
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + VERB
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")(?:"
                      + VERB
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + VERB
                      + a2
                      + ")(?:"
                      + Fc
                      + a2
                      + ")("
                      + CONJ
                      + l
                      + "type:C\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                      + r
                      + ")("
                      + VERB
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "CoordR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + VERB
                          + a2
                          + ")("
                          + Fc
                          + a2
                          + ")("
                          + CONJ
                          + l
                          + "type:C\\|"
                          + r
                          + "|"
                          + CONJ
                          + l
                          + "lemma:(?:and|or|y|e|et|o|ou)\\|"
                          + r
                          + ")("
                          + VERB
                          + a2
                          + ")",
                      "$3",
                      "",
                      true));
          Inherit(
              Pd.union()
                  .append(new Box[] {Casting.box("HeadDep"), Casting.box("mode,tense")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          Add(
              Pd.union()
                  .append(new Box[] {Casting.box("HeadDep"), Casting.box("coord:verb")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          /* SubjL: [NOUNCOORD] NOMINAL|PRO<type:D|P|I|X>  VERB<mode:[^G]>|CONJ<coord:verb&mode:[^G]>
          NEXT
          AdjnR: NOUNCOORD [NOMINAL|PRO<type:D|P|I|X>]  VERB<mode:[^G]>|CONJ<coord:verb&mode:[^G]>*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + NOUNCOORD
                      + ")("
                      + NOMINAL
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")("
                      + VERB
                      + l
                      + "mode:[^G]\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "coord:verb\\|"
                      + b2
                      + "mode:[^G]\\|"
                      + r
                      + ")",
                  "",
                  true);
          Rel = "SubjL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + NOUNCOORD
                      + ")(?:"
                      + NOMINAL
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")("
                      + VERB
                      + l
                      + "mode:[^G]\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "coord:verb\\|"
                      + b2
                      + "mode:[^G]\\|"
                      + r
                      + ")",
                  "",
                  true);
          Rel = "AdjnR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + NOUNCOORD
                          + ")("
                          + NOMINAL
                          + "|"
                          + PRO
                          + l
                          + "type:(?:D|P|I|X)\\|"
                          + r
                          + ")("
                          + VERB
                          + l
                          + "mode:[^G]\\|"
                          + r
                          + "|"
                          + CONJ
                          + l
                          + "coord:verb\\|"
                          + b2
                          + "mode:[^G]\\|"
                          + r
                          + ")",
                      "$1",
                      "",
                      true));
          /* SubjL: NOUN<type:P> VERB<mode:[^G]>|CONJ<coord:verb&mode:[^G]>
          Add: subj:yes*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + NOUN
                      + l
                      + "type:P\\|"
                      + r
                      + ")("
                      + VERB
                      + l
                      + "mode:[^G]\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "coord:verb\\|"
                      + b2
                      + "mode:[^G]\\|"
                      + r
                      + ")",
                  "",
                  true);
          Rel = "SubjL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + NOUN
                          + l
                          + "type:P\\|"
                          + r
                          + ")("
                          + VERB
                          + l
                          + "mode:[^G]\\|"
                          + r
                          + "|"
                          + CONJ
                          + l
                          + "coord:verb\\|"
                          + b2
                          + "mode:[^G]\\|"
                          + r
                          + ")",
                      "$2",
                      "",
                      true));
          Add(
              Pd.union()
                  .append(new Box[] {Casting.box("DepHead"), Casting.box("subj:yes")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          /* SubjL: NOMINAL|PRO<type:D|P|I|X>  VERB<mode:[^G]>|CONJ<coord:verb&mode:[^G]>
          Add: subj:yes*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + NOMINAL
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")("
                      + VERB
                      + l
                      + "mode:[^G]\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "coord:verb\\|"
                      + b2
                      + "mode:[^G]\\|"
                      + r
                      + ")",
                  "",
                  true);
          Rel = "SubjL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + NOMINAL
                          + "|"
                          + PRO
                          + l
                          + "type:(?:D|P|I|X)\\|"
                          + r
                          + ")("
                          + VERB
                          + l
                          + "mode:[^G]\\|"
                          + r
                          + "|"
                          + CONJ
                          + l
                          + "coord:verb\\|"
                          + b2
                          + "mode:[^G]\\|"
                          + r
                          + ")",
                      "$2",
                      "",
                      true));
          Add(
              Pd.union()
                  .append(new Box[] {Casting.box("DepHead"), Casting.box("subj:yes")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          /* PunctL: [NOUNCOORD|PRO<type:D|P|I|X>] Fc [PRO<type:R|W>] VERB<subj:yes>|CONJ<subj:yes&coord:verb>    [Fc]?
          NEXT
          PunctR: [NOUNCOORD|PRO<type:D|P|I|X>] [Fc] [PRO<type:R|W>] VERB<subj:yes>|CONJ<subj:yes&coord:verb>   Fc
          NEXT
          DobjL: [NOUNCOORD|PRO<type:D|P|I|X>] [Fc]? PRO<type:R|W> VERB<subj:yes>|CONJ<subj:yes&coord:verb>    [Fc]?
          NEXT
          AdjnR: NOUNCOORD|PRO<type:D|P|I|X> [Fc]? [PRO<type:R|W>] VERB<subj:yes>|CONJ<subj:yes&coord:verb>    [Fc]?*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")("
                      + Fc
                      + a2
                      + ")(?:"
                      + PRO
                      + l
                      + "type:(?:R|W)\\|"
                      + r
                      + ")("
                      + VERB
                      + l
                      + "subj:yes\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "subj:yes\\|"
                      + b2
                      + "coord:verb\\|"
                      + r
                      + ")(?:"
                      + Fc
                      + a2
                      + ")?",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")(?:"
                      + Fc
                      + a2
                      + ")(?:"
                      + PRO
                      + l
                      + "type:(?:R|W)\\|"
                      + r
                      + ")("
                      + VERB
                      + l
                      + "subj:yes\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "subj:yes\\|"
                      + b2
                      + "coord:verb\\|"
                      + r
                      + ")("
                      + Fc
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")(?:"
                      + Fc
                      + a2
                      + ")?("
                      + PRO
                      + l
                      + "type:(?:R|W)\\|"
                      + r
                      + ")("
                      + VERB
                      + l
                      + "subj:yes\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "subj:yes\\|"
                      + b2
                      + "coord:verb\\|"
                      + r
                      + ")(?:"
                      + Fc
                      + a2
                      + ")?",
                  "",
                  true);
          Rel = "DobjL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")(?:"
                      + Fc
                      + a2
                      + ")?(?:"
                      + PRO
                      + l
                      + "type:(?:R|W)\\|"
                      + r
                      + ")("
                      + VERB
                      + l
                      + "subj:yes\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "subj:yes\\|"
                      + b2
                      + "coord:verb\\|"
                      + r
                      + ")(?:"
                      + Fc
                      + a2
                      + ")?",
                  "",
                  true);
          Rel = "AdjnR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + NOUNCOORD
                          + "|"
                          + PRO
                          + l
                          + "type:(?:D|P|I|X)\\|"
                          + r
                          + ")("
                          + Fc
                          + a2
                          + ")?("
                          + PRO
                          + l
                          + "type:(?:R|W)\\|"
                          + r
                          + ")("
                          + VERB
                          + l
                          + "subj:yes\\|"
                          + r
                          + "|"
                          + CONJ
                          + l
                          + "subj:yes\\|"
                          + b2
                          + "coord:verb\\|"
                          + r
                          + ")("
                          + Fc
                          + a2
                          + ")?",
                      "$1",
                      "",
                      true));
          /* PunctL: [NOUNCOORD|PRO<type:D|P|I|X>] Fc [PRO<type:R|W>] VERB|CONJ<coord:verb>   [Fc]?
          NEXT
          PunctR: [NOUNCOORD|PRO<type:D|P|I|X>] [Fc]? [PRO<type:R|W>] VERB|CONJ<coord:verb> Fc
          NEXT
          SubjL: [NOUNCOORD|PRO<type:D|P|I|X>] [Fc]? PRO<type:R|W> VERB|CONJ<coord:verb>   [Fc]?
          NEXT
          AdjnR: NOUNCOORD|PRO<type:D|P|I|X> [Fc]? [PRO<type:R|W>] VERB|CONJ<coord:verb>  [Fc]?*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")("
                      + Fc
                      + a2
                      + ")(?:"
                      + PRO
                      + l
                      + "type:(?:R|W)\\|"
                      + r
                      + ")("
                      + VERB
                      + a2
                      + "|"
                      + CONJ
                      + l
                      + "coord:verb\\|"
                      + r
                      + ")(?:"
                      + Fc
                      + a2
                      + ")?",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")(?:"
                      + Fc
                      + a2
                      + ")?(?:"
                      + PRO
                      + l
                      + "type:(?:R|W)\\|"
                      + r
                      + ")("
                      + VERB
                      + a2
                      + "|"
                      + CONJ
                      + l
                      + "coord:verb\\|"
                      + r
                      + ")("
                      + Fc
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")(?:"
                      + Fc
                      + a2
                      + ")?("
                      + PRO
                      + l
                      + "type:(?:R|W)\\|"
                      + r
                      + ")("
                      + VERB
                      + a2
                      + "|"
                      + CONJ
                      + l
                      + "coord:verb\\|"
                      + r
                      + ")(?:"
                      + Fc
                      + a2
                      + ")?",
                  "",
                  true);
          Rel = "SubjL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")(?:"
                      + Fc
                      + a2
                      + ")?(?:"
                      + PRO
                      + l
                      + "type:(?:R|W)\\|"
                      + r
                      + ")("
                      + VERB
                      + a2
                      + "|"
                      + CONJ
                      + l
                      + "coord:verb\\|"
                      + r
                      + ")(?:"
                      + Fc
                      + a2
                      + ")?",
                  "",
                  true);
          Rel = "AdjnR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + NOUNCOORD
                          + "|"
                          + PRO
                          + l
                          + "type:(?:D|P|I|X)\\|"
                          + r
                          + ")("
                          + Fc
                          + a2
                          + ")?("
                          + PRO
                          + l
                          + "type:(?:R|W)\\|"
                          + r
                          + ")("
                          + VERB
                          + a2
                          + "|"
                          + CONJ
                          + l
                          + "coord:verb\\|"
                          + r
                          + ")("
                          + Fc
                          + a2
                          + ")?",
                      "$1",
                      "",
                      true));
          /* PunctL: [NOUNCOORD|PRO<type:D|P|I|X>] Fc [PRP] [PRO<type:R|W>] VERB|CONJ<coord:verb>   [Fc]?
          NEXT
          PunctR: [NOUNCOORD|PRO<type:D|P|I|X>] [Fc]?  [PRP] [PRO<type:R|W>] VERB|CONJ<coord:verb> Fc
          NEXT
          CircL: [NOUNCOORD|PRO<type:D|P|I|X>] [Fc]? PRP PRO<type:R|W> VERB|CONJ<coord:verb>  [Fc]?
          NEXT
          AdjnR: NOUNCOORD|PRO<type:D|P|I|X> [Fc]? [PRP] [PRO<type:R|W>] VERB|CONJ<coord:verb>  [Fc]?*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")("
                      + Fc
                      + a2
                      + ")(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + PRO
                      + l
                      + "type:(?:R|W)\\|"
                      + r
                      + ")("
                      + VERB
                      + a2
                      + "|"
                      + CONJ
                      + l
                      + "coord:verb\\|"
                      + r
                      + ")(?:"
                      + Fc
                      + a2
                      + ")?",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")(?:"
                      + Fc
                      + a2
                      + ")?(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + PRO
                      + l
                      + "type:(?:R|W)\\|"
                      + r
                      + ")("
                      + VERB
                      + a2
                      + "|"
                      + CONJ
                      + l
                      + "coord:verb\\|"
                      + r
                      + ")("
                      + Fc
                      + a2
                      + ")",
                  "",
                  true);
          Rel = "PunctR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")(?:"
                      + Fc
                      + a2
                      + ")?("
                      + PRP
                      + a2
                      + ")("
                      + PRO
                      + l
                      + "type:(?:R|W)\\|"
                      + r
                      + ")("
                      + VERB
                      + a2
                      + "|"
                      + CONJ
                      + l
                      + "coord:verb\\|"
                      + r
                      + ")(?:"
                      + Fc
                      + a2
                      + ")?",
                  "",
                  true);
          Rel = "CircL";
          RelDepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")(?:"
                      + Fc
                      + a2
                      + ")?(?:"
                      + PRP
                      + a2
                      + ")(?:"
                      + PRO
                      + l
                      + "type:(?:R|W)\\|"
                      + r
                      + ")("
                      + VERB
                      + a2
                      + "|"
                      + CONJ
                      + l
                      + "coord:verb\\|"
                      + r
                      + ")(?:"
                      + Fc
                      + a2
                      + ")?",
                  "",
                  true);
          Rel = "AdjnR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + NOUNCOORD
                          + "|"
                          + PRO
                          + l
                          + "type:(?:D|P|I|X)\\|"
                          + r
                          + ")("
                          + Fc
                          + a2
                          + ")?("
                          + PRP
                          + a2
                          + ")("
                          + PRO
                          + l
                          + "type:(?:R|W)\\|"
                          + r
                          + ")("
                          + VERB
                          + a2
                          + "|"
                          + CONJ
                          + l
                          + "coord:verb\\|"
                          + r
                          + ")("
                          + Fc
                          + a2
                          + ")?",
                      "$1",
                      "",
                      true));
          /* PunctL: [NOUNCOORD|PRO<type:D|P|I|X>] Fc VERB<mode:[GP]>|CONJ<coord:verb>
          NEXT
          AdjnR: NOUNCOORD|PRO<type:D|P|I|X> [Fc]? VERB<mode:[GP]>|CONJ<coord:verb>*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(?:"
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")("
                      + Fc
                      + a2
                      + ")("
                      + VERB
                      + l
                      + "mode:[GP]\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "coord:verb\\|"
                      + r
                      + ")",
                  "",
                  true);
          Rel = "PunctL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "("
                      + NOUNCOORD
                      + "|"
                      + PRO
                      + l
                      + "type:(?:D|P|I|X)\\|"
                      + r
                      + ")(?:"
                      + Fc
                      + a2
                      + ")?("
                      + VERB
                      + l
                      + "mode:[GP]\\|"
                      + r
                      + "|"
                      + CONJ
                      + l
                      + "coord:verb\\|"
                      + r
                      + ")",
                  "",
                  true);
          Rel = "AdjnR";
          HeadDep(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "("
                          + NOUNCOORD
                          + "|"
                          + PRO
                          + l
                          + "type:(?:D|P|I|X)\\|"
                          + r
                          + ")("
                          + Fc
                          + a2
                          + ")?("
                          + VERB
                          + l
                          + "mode:[GP]\\|"
                          + r
                          + "|"
                          + CONJ
                          + l
                          + "coord:verb\\|"
                          + r
                          + ")",
                      "$1",
                      "",
                      true));
          /* AdjnL: CONJ<type:S> VERB*/
          temp =
              Regex.matcher(
                  Casting.toString(listTags),
                  "(" + CONJ + l + "type:S\\|" + r + ")(" + VERB + a2 + ")",
                  "",
                  true);
          Rel = "AdjnL";
          DepHead(
              Pd.union()
                  .append(new Box[] {Casting.box(Rel), Casting.box("")})
                  .append(
                      (Box[])
                          Casting.cast(
                              new Casting() {
                                @Override
                                public Object casting(Object arg) {
                                  if (arg == null) {
                                    return null;
                                  }
                                  String[] col = (String[]) arg;
                                  Box[] col2 = new Box[col.length];
                                  for (int i = 0; i < col.length; i++) {
                                    col2[i] = Casting.box(col[i]);
                                  }
                                  return col2;
                                }
                              },
                              temp))
                  .toArray(Box[].class));
          listTags =
              Casting.toInteger(
                  Regex.substitution(
                      Casting.toString(listTags),
                      "(" + CONJ + l + "type:S\\|" + r + ")(" + VERB + a2 + ")",
                      "$2",
                      "",
                      true));
          /*############################# END GRAMMAR  ###############################*/
          if (Pd.compare(Pd.checkNull(ListInit), Pd.checkNull(Casting.toString(listTags))) == 0) {
            STOP = true;
          }
        }
        /*print STDERR "Iterations: $iteration\n";
        ########SAIDA CORRECTOR TAGGER*/
        if (Pd.compare(Pd.checkNull(flag), "-c") == 0) {
          for (i = 0;
              Pd.checkNull(i) <= Pd.checkNull((Token.size() - 1));
              i = Pd.checkNull(i) + 1) {
            Perl.print(Token.get(i) + "\t");
            PerlMap<String> OrdTags = new PerlMap<String>();
            OrdTags.put("tag", Tag.get(i));
            for (String feat : Perl.keys(ATTR.get(i))) {
              OrdTags.put(feat, ATTR.get(i).get(feat));
            }
            for (String feat : Perl.sort(Perl.keys(OrdTags))) {
              Perl.print(feat + ":" + OrdTags.get(feat) + "|");
            }
            Perl.print("\n");
          }
          /*#Colocar a zero os vectores de cada oraçao*/
          Token = new PerlList<String>();
          Tag = new PerlList<String>();
          Lemma = new PerlList<String>();
          Attributes = new PerlList<String>();
          ATTR = new PerlList<PerlMap<String>>();
        }
        /*########SAIDA STANDARD DO ANALISADOR */
        else if (Pd.compare(Pd.checkNull(flag), "-a") == 0) {
          /*#Escrever a oraçao que vai ser analisada:*/
          Perl.print("SENT::" + seq + "\n");
          /*print STDERR "LIST:: $listTags\n";
          ###imprimir Hash de dependencias ordenado:*/
          for (String triplet :
              Perl.sort(
                  Perl.keys(Ordenar),
                  (a, b) ->
                      Pd.compare(
                          Pd.checkNull(Casting.toString(Ordenar.get(a))),
                          Pd.checkNull(Casting.toString(Ordenar.get(b)))))) {
            triplet = Regex.substitution(triplet, "^\\(", "", "", false);
            triplet = Regex.substitution(triplet, "\\)$", "", "", false);
            String[] pd_4;
            pd_4 = Perl.split(";", triplet);
            String re = pd_4[0];
            String he = pd_4[1];
            String de = pd_4[2];
            if (!Regex.match(re, "(" + DepLex + ")", "", false)) {
              String[] pd_6;
              Object[] pd_5 = new Object[] {Perl.split("_", pd_1he)};
              pd_6 = Perl.split("_", pd_1he);
              String pd_1he = pd_6[0];
              String ta1 = pd_6[1];
              String pos1 = pd_6[2];
              pd_1he = Lemma.get(Casting.toInteger(pos1));
              String[] pd_8;
              Object[] pd_7 = new Object[] {Perl.split("_", pd_1de)};
              pd_8 = Perl.split("_", pd_1de);
              String pd_1de = pd_8[0];
              String ta2 = pd_8[1];
              String pos2 = pd_8[2];
              pd_1de = Lemma.get(Casting.toInteger(pos2));
              triplet =
                  re + ";" + pd_1he + "_" + ta1 + "_" + pos1 + ";" + pd_1de + "_" + ta2 + "_"
                      + pos2;
            }
            Perl.print("(" + triplet + ")\n");
          }
          /*#final de analise de frase:*/
          Perl.print("---\n");
        }
        /*##SAIDA ANALISADOR COM ESTRUTURA ATRIBUTO-VALOR (full analysis)*/
        else if (Pd.compare(Pd.checkNull(flag), "-fa") == 0) {
          /*#Escrever a oraçao que vai ser analisada:*/
          Perl.print("SENT::" + seq + "\n");
          /*print STDERR "LIST:: $listTags\n";
          ###imprimir Hash de dependencias ordenado:*/
          String re = "";
          for (String triplet :
              Perl.sort(
                  Perl.keys(Ordenar),
                  (a, b) ->
                      Pd.compare(
                          Pd.checkNull(Casting.toString(Ordenar.get(a))),
                          Pd.checkNull(Casting.toString(Ordenar.get(b)))))) {
            /* print "$triplet\n";*/
            triplet = Regex.substitution(triplet, "^\\(", "", "", false);
            triplet = Regex.substitution(triplet, "\\)$", "", "", false);
            String[] pd_9;
            pd_9 = Perl.split(";", triplet);
            String pd_1re = pd_9[0];
            String he = pd_9[1];
            String de = pd_9[2];
            if (!Regex.match(pd_1re, "(" + DepLex + ")", "", false)) {
                /*#se rel nao e lexical*/
              String[] pd_10;
              pd_10 = Perl.split("_", he);
              String he1 = pd_10[0];
              String ta1 = pd_10[1];
              String pos1 = pd_10[2];
              he1 = Lemma.get(Casting.toInteger(pos1));
              String[] pd_11;
              pd_11 = Perl.split("_", de);
              String de2 = pd_11[0];
              String ta2 = pd_11[1];
              String pos2 = pd_11[2];
              de2 = Lemma.get(Casting.toInteger(pos2));
              triplet =
                  pd_1re + ";" + he1 + "_" + ta1 + "_" + pos1 + ";" + de2 + "_" + ta2 + "_" + pos2;
            }
            Perl.print("(" + triplet + ")\n");
            String[] pd_13;
            Object[] pd_12 = new Object[] {Perl.split("_", pd_2he)};
            pd_13 = Perl.split("_", pd_2he);
            String pd_2he = pd_13[0];
            String ta = pd_13[1];
            String pos = pd_13[2];
            Perl.print("HEAD::" + pd_2he + "_" + ta + "_" + pos + "<");
            ATTR.get(Casting.toInteger(pos)).put("lemma", Lemma.get(Casting.toInteger(pos)));
            ATTR.get(Casting.toInteger(pos)).put("token", Token.get(Casting.toInteger(pos)));
            for (String feat : Perl.sort(Perl.keys(ATTR.get(Casting.toInteger(pos))))) {
              Perl.print(feat + ":" + ATTR.get(Casting.toInteger(pos)).get(feat) + "|");
            }
            Perl.print(">\n");
            String[] pd_15;
            Object[] pd_14 = new Object[] {Perl.split("_", pd_2de)};
            pd_15 = Perl.split("_", pd_2de);
            String pd_2de = pd_15[0];
            String pd_1ta = pd_15[1];
            String pd_1pos = pd_15[2];
            Perl.print("DEP::" + pd_2de + "_" + pd_1ta + "_" + pd_1pos + "<");
            ATTR.get(Casting.toInteger(pd_1pos))
                .put("lemma", Lemma.get(Casting.toInteger(pd_1pos)));
            ATTR.get(Casting.toInteger(pd_1pos))
                .put("token", Token.get(Casting.toInteger(pd_1pos)));
            for (String feat : Perl.sort(Perl.keys(ATTR.get(Casting.toInteger(pd_1pos))))) {
              Perl.print(feat + ":" + ATTR.get(Casting.toInteger(pd_1pos)).get(feat) + "|");
            }
            Perl.print(">\n");
            if (Regex.match(pd_1re, "/", "", false)) {
              String[] pd_16;
              pd_16 = Perl.split("/", pd_1re);
              String depName = pd_16[0];
              String reUnit = pd_16[1];
              String[] pd_17;
              pd_17 = Perl.split("_", reUnit);
              String reLex = pd_17[0];
              String pd_2ta = pd_17[1];
              String pd_2pos = pd_17[2];
              Perl.print("REL::" + reLex + "_" + pd_2ta + "_" + pd_2pos + "<");
              ATTR.get(Casting.toInteger(pd_2pos))
                  .put("lemma", Lemma.get(Casting.toInteger(pd_2pos)));
              ATTR.get(Casting.toInteger(pd_2pos))
                  .put("token", Token.get(Casting.toInteger(pd_2pos)));
              for (String feat : Perl.sort(Perl.keys(ATTR.get(Casting.toInteger(pd_2pos))))) {
                Perl.print(feat + ":" + ATTR.get(Casting.toInteger(pd_2pos)).get(feat) + "|");
              }
              Perl.print(">\n");
            }
          }
          /*#final de analise de frase:*/
          Perl.print("---\n");
        }
        /*#Colocar numa lista vazia os strings com os tags (listTags) e a oraçao (seq)
        #Borrar hash ordenado de triplets*/
        i = 0;
        j = 0;
        listTags = Casting.toInteger("");
        seq = Casting.toInteger("");
        Ordenar = new PerlMap<Integer>();
        Token = new PerlList<String>();
        Tag = new PerlList<String>();
        Lemma = new PerlList<String>();
        Attributes = new PerlList<String>();
        ATTR = new PerlList<PerlMap<String>>();
      } /*#fim do elsif*/
    }
    /*print "\n";*/
    Perl.print(PerlFile.STDERR, "Fim do parsing...\n");
  }

  static {
    global();
  }
}
