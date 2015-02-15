package net.client;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * Created by wangbin on 2015/2/13.
 */
public class MyCodecFactory  implements ProtocolCodecFactory {


    private final MyEncoder encoder;

    private final MyDecoder decoder;


    public MyCodecFactory(){
        encoder = new MyEncoder();
        decoder = new MyDecoder();
    }

    @Override
    public ProtocolEncoder getEncoder(IoSession session) throws Exception {
        return encoder;
    }

    @Override
    public ProtocolDecoder getDecoder(IoSession session) throws Exception {
       return decoder;
    }
}
