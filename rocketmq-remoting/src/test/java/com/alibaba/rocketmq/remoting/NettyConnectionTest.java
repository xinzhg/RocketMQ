package com.alibaba.rocketmq.remoting;

import org.junit.Test;

import com.alibaba.rocketmq.remoting.exception.RemotingConnectException;
import com.alibaba.rocketmq.remoting.exception.RemotingSendRequestException;
import com.alibaba.rocketmq.remoting.exception.RemotingTimeoutException;
import com.alibaba.rocketmq.remoting.netty.NettyClientConfig;
import com.alibaba.rocketmq.remoting.netty.NettyRemotingClient;
import com.alibaba.rocketmq.remoting.protocol.RemotingCommand;
import com.alibaba.rocketmq.remoting.protocol.RemotingProtos.RequestCode;


/**
 * ���ӳ�ʱ����
 * 
 * @author shijia.wxr<vintage.wang@gmail.com>
 * @since 2013-7-6
 */
public class NettyConnectionTest {
    public static RemotingClient createRemotingClient() {
        NettyClientConfig config = new NettyClientConfig();
        config.setClientChannelMaxIdleTimeSeconds(15);
        RemotingClient client = new NettyRemotingClient(config);
        client.start();
        return client;
    }


    @Test
    public void test_connect_timeout() throws InterruptedException, RemotingConnectException,
            RemotingSendRequestException, RemotingTimeoutException {
        RemotingClient client = createRemotingClient();

        for (int i = 0; i < 100; i++) {
            try {
                RemotingCommand request =
                        RemotingCommand.createRequestCommand(RequestCode.DEMO_REQUEST_VALUE, null);
                RemotingCommand response = client.invokeSync("127.0.0.1:8888", request, 1000 * 3);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        client.shutdown();
        System.out.println("-----------------------------------------------------------------");
    }
}
