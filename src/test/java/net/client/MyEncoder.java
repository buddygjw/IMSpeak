package net.client;

import com.bluemobi.imspeak.net.data.Packet;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

/**
 * Created by wangbin on 2015/2/13.
 */
public class MyEncoder implements ProtocolEncoder {


    @Override
    public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {

        Packet s = (Packet)message;

        if (s != null) {
            CharsetEncoder charsetEndoer = (CharsetEncoder)session.getAttribute("encoder");
            if (charsetEndoer == null) {
                charsetEndoer = Charset.defaultCharset().newEncoder();
                session.setAttribute("encoder", charsetEndoer);
            }
            IoBuffer ioBuffer = IoBuffer.allocate(s.getTextContent().length()+3);
            ioBuffer.setAutoExpand(true);
            ioBuffer.putInt(s.getStatus());
            ioBuffer.putInt(s.getDataType());
            ioBuffer.putInt(s.getCommandId());
            ioBuffer.putString(s.getTextContent(),charsetEndoer);
            ioBuffer.flip();
            out.write(ioBuffer);
        }

    }

    @Override
    public void dispose(IoSession session) throws Exception {

    }
}
