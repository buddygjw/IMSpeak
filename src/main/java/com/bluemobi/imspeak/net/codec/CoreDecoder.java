/*
 * Copyright (C) 2010 Moduad Co., Ltd.
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package com.bluemobi.imspeak.net.codec;

import com.bluemobi.imspeak.net.data.Packet;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/** 
 * Decoder class that parses ByteBuffers and generates XML stanzas.
 *
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public class CoreDecoder extends CumulativeProtocolDecoder {

    private Charset charset = Charset.forName("UTF-8");

    @Override
    protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {

        if (in.hasRemaining()) {
            int status =   in.getInt();
            int dataType = in.getInt();
            int commandId = in.getInt();
            String content =  in.getString(charset.newDecoder());
            Packet packet = new Packet();
            packet.setStatus(status);
            packet.setDataType(dataType);
            packet.setCommandId(commandId);
            packet.setTextContent(content);
            out.write(packet);
            return true;
        }
        return false;
    }

    // private final Log log = LogFactory.getLog(XmppDecoder.class);




}
