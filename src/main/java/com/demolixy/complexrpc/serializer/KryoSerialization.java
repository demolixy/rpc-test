/**
 * 
 */
package com.demolixy.complexrpc.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Registration;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

/**
 * @author lixiangyang
 *
 */
public class KryoSerialization {
    private Kryo kryo;

    private Registration registration;

    private Class<?> t;

    public KryoSerialization() {
        kryo = new Kryo();
        kryo.setReferences(true);
    }

    public void register(Class<?> T) {
        //注册类 
        t = T;
        registration = kryo.register(t);
    }

    public byte[] Serialize(Object object) {
        Output output = null;
        output = new Output(1, 4096);
        kryo.writeClassAndObject(output, object);
        byte[] bb = output.toBytes();
        output.flush();

        return bb;
    }

    public <t> t Deserialize(byte[] bb) {
        Input input = null;
        input = new Input(bb);
        @SuppressWarnings("unchecked")
        t res = (t) kryo.readClassAndObject(input);
        input.close();
        return res;
    }
}

