package ai.cotogoto.noby;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hidekazu.aoshima on 04/23/16.
 */
public class Parameters implements Serializable {

    /** serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** parameters. */
    private List <Parameter>  parameters       = new ArrayList <Parameter>();


    /**
     * @param name String
     * @param value String
     */
    public final void addParameter(final String name, final String value) {

        this.parameters.add(new Parameter(name, value));
    }


    /**
     * @return parameterList
     */
    public final List <Parameter> getParameters() {

        return this.parameters;
    }


    /**
     * @param pParameters Set parameters
     */
    public final void setParameters(final List <Parameter> pParameters) {

        this.parameters = pParameters;
    }

    /**
    * Created by hidekazu.aoshima on 04/23/16.
    */
    class Parameter implements Serializable {

        /**
         * serialVersionUID.
         */
        private static final long serialVersionUID = 1L;

        /** name. */
        private String            name;

        /** value. */
        private String            value;


        /**
         * constructor.
         * @param pName String
         * @param pValue String
         */
        public Parameter(final String pName, final String pValue) {

            this.name = pName;
            this.value = pValue;
        }


        /**
         * @return name
         */
        public String getName() {

            return this.name;
        }


        /**
         * @param pName Set name
         */
        public void setName(final String pName) {

            this.name = pName;
        }


        /**
         * @return value
         */
        public String getValue() {

            return this.value;
        }


        /**
         * @param pValue Set value
         */
        public void setValue(final String pValue) {

            this.value = pValue;
        }

    }


    /*
     * (非 Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public final String toString() {

        final StringBuffer sb = new StringBuffer();

        boolean first = true;
        for (final Parameter param : this.parameters) {
            if (first) {
                sb.append(param.getName() + "=" + param.getValue());
                first = false;
            } else {
                sb.append("&" + param.getName() + "=" + param.getValue());
            }
        }
        return sb.toString();
    }
}
