/*
 * Copyright 2015, Google Inc. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 *    * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *    * Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following disclaimer
 * in the documentation and/or other materials provided with the
 * distribution.
 *
 *    * Neither the name of Google Inc. nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package sr.grpc.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;

import sr.grpc.gen.*;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LabClient {
    private static final Logger logger = Logger.getLogger(LabClient.class.getName());

    private final ManagedChannel channel;
    private final UserManagerGrpc.UserManagerBlockingStub userManagerBlockingStub;
    private final DeviceManagerGrpc.DeviceManagerBlockingStub deviceManagerBlockingStub;
    private final DeviceManagerGrpc.DeviceManagerStub deviceManagerAsyncStub;

    private Integer id;

    public LabClient(String host, int port) {
        channel = ManagedChannelBuilder.forAddress(host, port)
                // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid needing certificates.
                .usePlaintext(true)
                .build();

        userManagerBlockingStub = UserManagerGrpc.newBlockingStub(channel);
        deviceManagerBlockingStub = DeviceManagerGrpc.newBlockingStub(channel);
        deviceManagerAsyncStub = DeviceManagerGrpc.newStub(channel);
    }

    public static void main(String[] args) throws Exception {
        LabClient client = new LabClient("localhost", 50051);
        client.start();
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }


    public void start() throws InterruptedException {
        try {
            System.out.println("Registering user...");
            RegistrationTicket regrequest = RegistrationTicket.newBuilder().setSomething("kek").build();
            RegistrationResult regresult = userManagerBlockingStub.registerUser(regrequest);

            this.id = regresult.getId();
            if (this.id == null) {
                System.out.println("Registration failed.");
                shutdown();
                return;
            }

            System.out.println("User registered with id = " + this.id);

            boolean done = false;
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            while (!done) {
                String message = br.readLine();
                if (message.startsWith("dev ")) {
                    ActionParams request = ActionParams.newBuilder().setUserId(this.id)
                            .setParams(message.substring(4)).build();
                    ActionResult result = deviceManagerBlockingStub.action(request);

                    System.out.println("Action performed. Result: " + result.getResult());
                } else if (message.equalsIgnoreCase("list")) {
                    ListDevicesRequest request = ListDevicesRequest.newBuilder().setUserId(this.id).build();
                    Iterator<DeviceInfo> infos;
                    try {
                        infos = deviceManagerBlockingStub.listDevices(request);
                        while (infos.hasNext()) {
                            DeviceInfo devinfo = infos.next();
                            System.out.println(">> " + devinfo.getInfo());
                        }
                    } catch (StatusRuntimeException ex) {
                        logger.log(Level.WARNING, "RPC failed: {0}", ex.getStatus());
                        return;
                    }

                } else if (message.startsWith("control ")) {
                    String deviceName = message.substring(8);
                    ControlDeviceRequest request = ControlDeviceRequest.newBuilder().setUserId(this.id)
                            .setDeviceName(deviceName).build();
                    ControlDeviceResponse result = deviceManagerBlockingStub.controlDevice(request);

                    boolean accepted = result.getAccepted();
                    if (!accepted) {
                        System.out.println("Request rejected.");
                    } else {
                        System.out.println("Request accepted. Now controlling the device.\nInstruction:");
                        System.out.println(result.getIntruction());
                    }
                } else if (message.equalsIgnoreCase("release")) {
                    ReleaseDeviceRequest request = ReleaseDeviceRequest.newBuilder().setUserId(this.id).build();
                    ReleaseDeviceResponse result = deviceManagerBlockingStub.releaseDevice(request);

                    boolean accepted = result.getAccepted();
                    if (!accepted) {
                        System.out.println("Request rejected.");
                    } else {
                        System.out.println("Gave up control over device.");
                    }
                } else if (message.startsWith("observe ")) {
                    String deviceName = message.substring(8);
                    ObserveDeviceRequest request = ObserveDeviceRequest.newBuilder().setUserId(this.id)
                            .setDeviceName(deviceName).build();
                    deviceManagerAsyncStub.observeDevice(request,
                            new StreamObserver<ObserveDeviceResponse>() {
                                @Override
                                public void onNext(ObserveDeviceResponse observeDeviceResponse) {
                                    if (!observeDeviceResponse.getAccepted()) {
                                        System.out.println("Request rejected.");
                                    } else {
                                        System.out.println("----------");
                                        System.out.println("command: " + observeDeviceResponse.getCommand());
                                        System.out.println("output: " + observeDeviceResponse.getResult());
                                        System.out.println("----------");
                                    }
                                }

                                @Override
                                public void onError(Throwable throwable) {
                                    throwable.printStackTrace();
                                }

                                @Override
                                public void onCompleted() {
                                    System.out.println("Observing finished.");
                                }
                            });

//                    if (!accepted) {
//                        System.out.println("Request rejected.");
//                    } else {
//                        System.out.println("Request accepted. Now observing the device.");
//                    }
                } else if (message.equalsIgnoreCase("unobserve")) {
                    UnobserveDeviceRequest request = UnobserveDeviceRequest.newBuilder().setUserId(this.id).build();
                    UnobserveDeviceResponse result = deviceManagerBlockingStub.unobserveDevice(request);

                    boolean accepted = result.getAccepted();
                    if (!accepted) {
                        System.out.println("Request rejected.");
                    } else {
                        System.out.println("Stopped observing the device.");
                    }
                } else if (message.equalsIgnoreCase("exit")) {
                    done = true;
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            shutdown();
        }

    }
}
