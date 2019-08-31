package jolie.typeChecker;

import jolie.lang.NativeType;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

public class TypeCheckerWriter {

    private Writer writer;
    private StringBuilder sb;
    private String declarationString = "";
    private StatementsContext context;

    public TypeCheckerWriter(Writer writer) throws IOException {
        this.writer = writer;
        sb = new StringBuilder();

        context = new StatementsContext();

        sb.append(
                "(declare-sort Type)\n" +
                        "(declare-sort Term)\n" +
                        "\n" +
                        "(declare-fun hasType (Term Type) Bool)\n" +
                        "(declare-fun sameType (Term Term) Bool)\n" +
                        "(declare-fun typeOf (Term) Type)\n" +
                        "\n"
        );

        NativeType[] nativeTypes = NativeType.values();
        int nativeTypesLength = nativeTypes.length;

        String[] types = new String[nativeTypesLength];

        for (int i = 0; i < nativeTypesLength; i++) {
            NativeType type = nativeTypes[i];

            // we don't need 'any' type here, but we do need 'undefined', so just replace it
            if (!type.equals(NativeType.ANY)) {
                types[i] = type.id();
            } else {
                types[i] = "undefined";
            }
        }

        sb.append(";; Define types\n");
        for (String type : types) {
            sb.append("(declare-fun ").append(type).append(" () Type)\n");
        }
        sb.append("(declare-fun any () Type)\n"); // TODO clarify equality with other types
        sb.append("\n");

        sb.append(";; Ensure non-equality of types\n");
        for (int i = 0; i < types.length; i++) {
            for (int j = i + 1; j < types.length; j++) {
                sb.append("(assert (not (= ").append(types[i]).append(" ").append(types[j]).append(")))\n");
            }
            sb.append("\n");
        }

        sb.append(";; Describe type functions behavior\n");
        for (String type : types) {
            sb.append("(assert (forall ((t Term)) (= (hasType t ").append(type).append(") (= (typeOf t) ").append(type).append("))))\n");
        }

        sb.append("\n");
        sb.append("(assert (forall ((t1 Term) (t2 Term)) (= (sameType t1 t2) (= (typeOf t1) (typeOf t2)))))\n");
        sb.append("\n");

        sb.append(";; Program constraints\n");
    }

    protected void flush() throws IOException {
        writer.write(sb.toString());
        writer.write("\n(check-sat)\n");
        writer.flush();
    }

    public void enterScope() {
        sb.append("\n(push)\n");
        context = context.push();
    }

    public void exitScope() {
        sb.append("\n")
                .append("(check-sat)\n")
                .append("\n")
                .append("(pop)\n")
                .append("\n");

        try {
            context = context.pop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void write(String s) {
        sb.append(s);
    }

    public void writeLine() {
        sb.append("\n");
    }

    public void writeLine(String s) {
        sb.append(s);
        sb.append("\n");
    }

    public boolean declareTermOnce(String name) {
        if (!context.contains(name)) {
            writeLine("(declare-const " + name + " Term)");
            List<String> inferedType = TypeCheckerVisitor.getEnforceTypeChecking();
            if (!name.contains("$$__term_id_") && !inferedType.isEmpty()) {
                String copiedInferedType = inferedType.toString();
                copiedInferedType = copiedInferedType.replace("[(assert ", "");
                copiedInferedType = copiedInferedType.replace(" (assert ", "");
                copiedInferedType = copiedInferedType.replace("(hasType ", "");

                if (copiedInferedType.contains("int")) {
                    copiedInferedType = copiedInferedType.replace("int))\n]", "");
                    copiedInferedType = copiedInferedType.replace("int))\n,", "");
                }
                if (copiedInferedType.contains("string")) {
                    copiedInferedType = copiedInferedType.replace("string))\n]", "");
                    copiedInferedType = copiedInferedType.replace("string))\n,", "");
                }

                String[] listOfNames = copiedInferedType.split(" ");

                //Removing Duplicates
                //Create set from array elements
                LinkedHashSet<String> linkedHashSet = new LinkedHashSet<>( Arrays.asList(listOfNames) );

                //Get back the array without duplicates
                listOfNames = linkedHashSet.toArray(new String[] {});

                for (int i = 0; i < listOfNames.length; i++) {
                    if ((listOfNames[i].compareTo(name) == 0)) {
                        for (int j = 0; j < inferedType.size(); j++) {
                            if (inferedType.get(j).contains(name)) {
                                write(inferedType.get(j));
                            }
                        }
                    }
                }
            }
            context.add(name);
            return true;
        }
        return false;
    }


    public void assertTypeLikeBoolean(String termId) {
        oneOfTypes(termId,
                TermType.BOOL,
                TermType.DOUBLE,
                TermType.INT,
                TermType.LONG,
                TermType.STRING);
    }

    public void assertTypeNumber(String termId) {
        oneOfTypes(termId,
                TermType.INT,
                TermType.LONG,
                TermType.DOUBLE);
    }

    public void oneOfTypes(String termId, TermType... types) {
        sb.append("(assert (or ");
        for (TermType type : types) {
            sb.append("(hasType ").append(termId).append(" ").append(type.id()).append(") ");
        }
        sb.append("))\n");
    }
}
