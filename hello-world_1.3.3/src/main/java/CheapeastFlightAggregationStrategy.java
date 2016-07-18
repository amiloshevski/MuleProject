import org.mule.DefaultMuleEvent;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.routing.AggregationContext;
import org.mule.routing.AggregationStrategy;

public class CheapeastFlightAggregationStrategy implements AggregationStrategy {
 
    @Override
    public MuleEvent aggregate(AggregationContext context) throws MuleException {
        MuleEvent result = null;
        long value = Long.MAX_VALUE;
        for (MuleEvent event : context.collectEventsWithoutExceptions()) {
            Flight flight = (Flight) event.getMessage().getPayload();
            if (flight.getCost() < value) {
                result = DefaultMuleEvent.copy(event);
                value = flight.getCost();
            }
        }
         
        if (result != null)  {
            return result;
        }
         
        throw new  RuntimeException("no flights obtained");
    }
}