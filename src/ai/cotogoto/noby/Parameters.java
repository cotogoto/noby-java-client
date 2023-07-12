package ai.cotogoto.noby;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import lombok.Data;

/**
 * The Parameters class is used to maintain a list of parameters and
 * convert them into a URL query string format.
 *
 * @author H.Aoshima
 * @version 1.0
 */
@Data
public class Parameters implements Serializable {

    private static final long serialVersionUID = 1L;

    private List <Parameter>  parameters       = new CopyOnWriteArrayList <>();

    /**
     * The Parameter inner class is used to encapsulate a single parameter which
     * includes its name and corresponding value.
     */
    @Data
    class Parameter implements Serializable {

        private static final long serialVersionUID = 1L;

        private final String      name;

        private final String      value;

        /**
         * Constructs a new Parameter with the specified name and value.
         *
         * @param name The name of the parameter.
         * @param value The value of the parameter.
         */
        Parameter(final String name, final String value) {

            this.name = name;
            this.value = value;
        }
    }

    /**
     * Adds a new Parameter to the list of parameters. The value is
     * converted to a string before being stored.
     *
     * @param name The name of the parameter.
     * @param value The value of the parameter.
     */
    public final void addParameter(final String name, final Object value) {

        this.parameters.add(new Parameter(name, String.valueOf(value)));
    }


    /**
     * Returns a copy of the current list of parameters.
     *
     * @return The current list of parameters.
     */
    public final List <Parameter> getParameters() {

        return new CopyOnWriteArrayList <>(this.parameters);
    }


    /**
     * Replaces the current list of parameters with the specified list.
     *
     * @param parameters The new list of parameters.
     */
    public final void setParameters(final List <Parameter> parameters) {

        this.parameters = new CopyOnWriteArrayList <>(parameters);
    }


    /**
     * Returns a string representation of the parameters in the form of a
     * URL query string.
     *
     * @return A string representation of the parameters.
     */
    @Override
    public final String toString() {

        return this.parameters.stream()
                .map(param -> param.getName() + "=" + param.getValue())
                .collect(Collectors.joining("&"));
    }
}
