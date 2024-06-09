package com.gnivc.portalservice.model.dadata;

import lombok.Data;
import java.util.List;

@Data
public class DaDataSuggestionResponse {
    private List<Suggestion> suggestions;

    @Data
    public static class Suggestion {
        private String value;
        private String unrestrictedValue;
        private DataDTO data;

        @Data
        public static class DataDTO {
            private String kpp;
            private CapitalDTO capital;
            private ManagementDTO management;
            private List<FounderDTO> founders;
            private List<ManagerDTO> managers;
            private String branchType;
            private int branchCount;
            private String hid;
            private String type;
            private StateDTO state;
            private OpfDTO opf;
            private NameDTO name;
            private String inn;
            private String ogrn;
            private String okpo;
            private String okato;
            private String oktmo;
            private String okogu;
            private String okfs;
            private String okved;
            private List<OkvedDTO> okveds;
            private AuthoritiesDTO authorities;
            private DocumentsDTO documents;
            private FinanceDTO finance;
            private AddressDTO address;
            private List<PhoneDTO> phones;
            private List<EmailDTO> emails;
            private long ogrnDate;
            private String okvedType;
            private int employeeCount;
        }

        @Data
        public static class CapitalDTO {
            private String type;
            private double value;
        }

        @Data
        public static class ManagementDTO {
            private String name;
            private String post;
            private String disqualified;
        }

        @Data
        public static class FounderDTO {
            private String ogrn;
            private String inn;
            private String name;
            private String hid;
            private String type;
            private ShareDTO share;
        }

        @Data
        public static class ShareDTO {
            private double value;
            private String type;
        }

        @Data
        public static class ManagerDTO {
            private String inn;
            private FioDTO fio;
            private String post;
            private String hid;
            private String type;
        }

        @Data
        public static class FioDTO {
            private String surname;
            private String name;
            private String patronymic;
            private String gender;
            private String source;
            private String qc;
        }

        @Data
        public static class StateDTO {
            private String status;
            private String code;
            private long actualityDate;
            private long registrationDate;
            private String liquidationDate;
        }

        @Data
        public static class OpfDTO {
            private String type;
            private String code;
            private String full;
            private String shortName;
        }

        @Data
        public static class NameDTO {
            private String fullWithOpf;
            private String shortWithOpf;
            private String latin;
            private String full;
            private String shortName;
        }

        @Data
        public static class OkvedDTO {
            private boolean main;
            private String type;
            private String code;
            private String name;
        }

        @Data
        public static class AuthoritiesDTO {
            private FtsRegistrationDTO ftsRegistration;
            private FtsReportDTO ftsReport;
            private PfDTO pf;
            private SifDTO sif;
        }

        @Data
        public static class FtsRegistrationDTO {
            private String type;
            private String code;
            private String name;
            private String address;
        }

        @Data
        public static class FtsReportDTO {
            private String type;
            private String code;
            private String name;
            private String address;
        }

        @Data
        public static class PfDTO {
            private String type;
            private String code;
            private String name;
            private String address;
        }

        @Data
        public static class SifDTO {
            private String type;
            private String code;
            private String name;
            private String address;
        }

        @Data
        public static class DocumentsDTO {
            private FtsRegistrationDocumentDTO ftsRegistration;
            private FtsReportDocumentDTO ftsReport;
            private PfRegistrationDTO pfRegistration;
            private SifRegistrationDTO sifRegistration;
            private SmbDTO smb;
        }

        @Data
        public static class FtsRegistrationDocumentDTO {
            private String type;
            private String series;
            private String number;
            private long issueDate;
            private String issueAuthority;
        }

        @Data
        public static class FtsReportDocumentDTO {
            private String type;
            private String series;
            private String number;
            private long issueDate;
            private String issueAuthority;
        }

        @Data
        public static class PfRegistrationDTO {
            private String type;
            private String series;
            private String number;
            private long issueDate;
            private String issueAuthority;
        }

        @Data
        public static class SifRegistrationDTO {
            private String type;
            private String series;
            private String number;
            private long issueDate;
            private String issueAuthority;
        }

        @Data
        public static class SmbDTO {
            private String category;
            private String type;
            private String series;
            private String number;
            private long issueDate;
            private String issueAuthority;
        }

        @Data
        public static class FinanceDTO {
            private String taxSystem;
            private double income;
            private double expense;
            private double revenue;
            private String debt;
            private String penalty;
            private int year;
        }

        @Data
        public static class AddressDTO {
            private String value;
            private String unrestrictedValue;
            private AddressDataDTO data;
        }

        @Data
        public static class AddressDataDTO {
        }

        @Data
        public static class PhoneDTO {
            private String value;
            private String unrestrictedValue;
            private PhoneDataDTO data;
        }

        @Data
        public static class PhoneDataDTO {
            private String contact;
            private String source;
            private String qc;
            private String type;
            private String number;
            private String extension;
            private String provider;
            private String country;
            private String region;
            private String city;
            private String timezone;
            private String countryCode;
            private String cityCode;
            private String qcConflict;
        }

        @Data
        public static class EmailDTO {
            private String value;
            private String unrestrictedValue;
            private EmailDataDTO data;
        }

        @Data
        public static class EmailDataDTO {
            private String local;
            private String domain;
            private String type;
            private String source;
            private String qc;
        }
    }
}
