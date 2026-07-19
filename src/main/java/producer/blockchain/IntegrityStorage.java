package producer.blockchain;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/main/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.6.0.
 */
@SuppressWarnings("rawtypes")
public class IntegrityStorage extends Contract {
    public static final String BINARY = "6080604052348015600e575f5ffd5b50610b938061001c5f395ff3fe608060405234801561000f575f5ffd5b506004361061004a575f3560e01c806303e9e6091461004e57806334461067146100805780633f723c62146100b2578063a87d942c146100ce575b5f5ffd5b61006860048036038101906100639190610587565b6100ec565b60405161007793929190610622565b60405180910390f35b61009a60048036038101906100959190610587565b6102e9565b6040516100a993929190610622565b60405180910390f35b6100cc60048036038101906100c79190610798565b6104af565b005b6100d6610538565b6040516100e3919061084b565b60405180910390f35b60608060605f5f858154811061010557610104610864565b5b905f5260205f2090600302016040518060600160405290815f8201805461012b906108be565b80601f0160208091040260200160405190810160405280929190818152602001828054610157906108be565b80156101a25780601f10610179576101008083540402835291602001916101a2565b820191905f5260205f20905b81548152906001019060200180831161018557829003601f168201915b505050505081526020016001820180546101bb906108be565b80601f01602080910402602001604051908101604052809291908181526020018280546101e7906108be565b80156102325780601f1061020957610100808354040283529160200191610232565b820191905f5260205f20905b81548152906001019060200180831161021557829003601f168201915b5050505050815260200160028201805461024b906108be565b80601f0160208091040260200160405190810160405280929190818152602001828054610277906108be565b80156102c25780601f10610299576101008083540402835291602001916102c2565b820191905f5260205f20905b8154815290600101906020018083116102a557829003601f168201915b5050505050815250509050805f015181602001518260400151935093509350509193909250565b5f81815481106102f7575f80fd5b905f5260205f2090600302015f91509050805f018054610316906108be565b80601f0160208091040260200160405190810160405280929190818152602001828054610342906108be565b801561038d5780601f106103645761010080835404028352916020019161038d565b820191905f5260205f20905b81548152906001019060200180831161037057829003601f168201915b5050505050908060010180546103a2906108be565b80601f01602080910402602001604051908101604052809291908181526020018280546103ce906108be565b80156104195780601f106103f057610100808354040283529160200191610419565b820191905f5260205f20905b8154815290600101906020018083116103fc57829003601f168201915b50505050509080600201805461042e906108be565b80601f016020809104026020016040519081016040528092919081815260200182805461045a906108be565b80156104a55780601f1061047c576101008083540402835291602001916104a5565b820191905f5260205f20905b81548152906001019060200180831161048857829003601f168201915b5050505050905083565b5f604051806060016040528085815260200184815260200183815250908060018154018082558091505060019003905f5260205f2090600302015f909190919091505f820151815f0190816105049190610a8e565b50602082015181600101908161051a9190610a8e565b5060408201518160020190816105309190610a8e565b505050505050565b5f5f80549050905090565b5f604051905090565b5f5ffd5b5f5ffd5b5f819050919050565b61056681610554565b8114610570575f5ffd5b50565b5f813590506105818161055d565b92915050565b5f6020828403121561059c5761059b61054c565b5b5f6105a984828501610573565b91505092915050565b5f81519050919050565b5f82825260208201905092915050565b8281835e5f83830152505050565b5f601f19601f8301169050919050565b5f6105f4826105b2565b6105fe81856105bc565b935061060e8185602086016105cc565b610617816105da565b840191505092915050565b5f6060820190508181035f83015261063a81866105ea565b9050818103602083015261064e81856105ea565b9050818103604083015261066281846105ea565b9050949350505050565b5f5ffd5b5f5ffd5b7f4e487b71000000000000000000000000000000000000000000000000000000005f52604160045260245ffd5b6106aa826105da565b810181811067ffffffffffffffff821117156106c9576106c8610674565b5b80604052505050565b5f6106db610543565b90506106e782826106a1565b919050565b5f67ffffffffffffffff82111561070657610705610674565b5b61070f826105da565b9050602081019050919050565b828183375f83830152505050565b5f61073c610737846106ec565b6106d2565b90508281526020810184848401111561075857610757610670565b5b61076384828561071c565b509392505050565b5f82601f83011261077f5761077e61066c565b5b813561078f84826020860161072a565b91505092915050565b5f5f5f606084860312156107af576107ae61054c565b5b5f84013567ffffffffffffffff8111156107cc576107cb610550565b5b6107d88682870161076b565b935050602084013567ffffffffffffffff8111156107f9576107f8610550565b5b6108058682870161076b565b925050604084013567ffffffffffffffff81111561082657610825610550565b5b6108328682870161076b565b9150509250925092565b61084581610554565b82525050565b5f60208201905061085e5f83018461083c565b92915050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52603260045260245ffd5b7f4e487b71000000000000000000000000000000000000000000000000000000005f52602260045260245ffd5b5f60028204905060018216806108d557607f821691505b6020821081036108e8576108e7610891565b5b50919050565b5f819050815f5260205f209050919050565b5f6020601f8301049050919050565b5f82821b905092915050565b5f6008830261094a7fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff8261090f565b610954868361090f565b95508019841693508086168417925050509392505050565b5f819050919050565b5f61098f61098a61098584610554565b61096c565b610554565b9050919050565b5f819050919050565b6109a883610975565b6109bc6109b482610996565b84845461091b565b825550505050565b5f5f905090565b6109d36109c4565b6109de81848461099f565b505050565b5b81811015610a01576109f65f826109cb565b6001810190506109e4565b5050565b601f821115610a4657610a17816108ee565b610a2084610900565b81016020851015610a2f578190505b610a43610a3b85610900565b8301826109e3565b50505b505050565b5f82821c905092915050565b5f610a665f1984600802610a4b565b1980831691505092915050565b5f610a7e8383610a57565b9150826002028217905092915050565b610a97826105b2565b67ffffffffffffffff811115610ab057610aaf610674565b5b610aba82546108be565b610ac5828285610a05565b5f60209050601f831160018114610af6575f8415610ae4578287015190505b610aee8582610a73565b865550610b55565b601f198416610b04866108ee565b5f5b82811015610b2b57848901518255600182019150602085019450602081019050610b06565b86831015610b485784890151610b44601f891682610a57565b8355505b6001600288020188555050505b50505050505056fea2646970667358221220926d636a782d73f9429a90eb22ec9c09c4e73c24dd80635d8fc214d7344bb76b64736f6c634300081f0033";

    private static String librariesLinkedBinary;

    public static final String FUNC_ADDRECORD = "addRecord";

    public static final String FUNC_GETCOUNT = "getCount";

    public static final String FUNC_GETRECORD = "getRecord";

    public static final String FUNC_RECORDS = "records";

    @Deprecated
    protected IntegrityStorage(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected IntegrityStorage(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected IntegrityStorage(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected IntegrityStorage(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> addRecord(String deviceId, String timestamp,
            String hashValue) {
        final Function function = new Function(
                FUNC_ADDRECORD, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(deviceId), 
                new org.web3j.abi.datatypes.Utf8String(timestamp), 
                new org.web3j.abi.datatypes.Utf8String(hashValue)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> getCount() {
        final Function function = new Function(FUNC_GETCOUNT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Tuple3<String, String, String>> getRecord(BigInteger index) {
        final Function function = new Function(FUNC_GETRECORD, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(index)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteFunctionCall<Tuple3<String, String, String>>(function,
                new Callable<Tuple3<String, String, String>>() {
                    @Override
                    public Tuple3<String, String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<String, String, String>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue());
                    }
                });
    }

    public RemoteFunctionCall<Tuple3<String, String, String>> records(BigInteger param0) {
        final Function function = new Function(FUNC_RECORDS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteFunctionCall<Tuple3<String, String, String>>(function,
                new Callable<Tuple3<String, String, String>>() {
                    @Override
                    public Tuple3<String, String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<String, String, String>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue());
                    }
                });
    }

    @Deprecated
    public static IntegrityStorage load(String contractAddress, Web3j web3j,
            Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new IntegrityStorage(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static IntegrityStorage load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new IntegrityStorage(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static IntegrityStorage load(String contractAddress, Web3j web3j,
            Credentials credentials, ContractGasProvider contractGasProvider) {
        return new IntegrityStorage(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static IntegrityStorage load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new IntegrityStorage(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<IntegrityStorage> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(IntegrityStorage.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<IntegrityStorage> deploy(Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(IntegrityStorage.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    public static RemoteCall<IntegrityStorage> deploy(Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(IntegrityStorage.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<IntegrityStorage> deploy(Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(IntegrityStorage.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    public static void linkLibraries(List<Contract.LinkReference> references) {
        librariesLinkedBinary = linkBinaryWithReferences(BINARY, references);
    }

    private static String getDeploymentBinary() {
        if (librariesLinkedBinary != null) {
            return librariesLinkedBinary;
        } else {
            return BINARY;
        }
    }
}
