package com.bluemobi.imspeak.net.codec;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * Created by wangbin on 2015/2/11.
 */
public class CoreCodecFactory implements ProtocolCodecFactory {

    private final CoreEncoder encoder;
    private final CoreDecoder decoder;

    /**
     * Constructor.
     */
    public CoreCodecFactory() {
        encoder = new CoreEncoder();
        decoder = new CoreDecoder();
    }

    /**
     * Returns a new (or reusable) instance of ProtocolEncoder.
     */
    public ProtocolEncoder getEncoder(IoSession session) throws Exception {
        return encoder;
    }

    /**
     * Returns a new (or reusable) instance of ProtocolDecoder.
     */
    public ProtocolDecoder getDecoder(IoSession session) throws Exception {
        return decoder;
    }

}
