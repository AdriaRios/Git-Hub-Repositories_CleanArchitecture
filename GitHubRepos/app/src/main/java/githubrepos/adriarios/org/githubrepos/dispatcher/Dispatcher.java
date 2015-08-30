package githubrepos.adriarios.org.githubrepos.dispatcher;

import com.squareup.otto.Bus;

/**
 * Created by Adrian on 13/08/2015.
 */
public class Dispatcher {
    Bus dispatcher = new Bus();
    public Dispatcher(){
        dispatcher = new Bus();
    }

    public void dispatch(Object event){
        dispatcher.post(event);
    }
    public void subscribe(Object subscriber){
        dispatcher.register(subscriber);
    }
}
