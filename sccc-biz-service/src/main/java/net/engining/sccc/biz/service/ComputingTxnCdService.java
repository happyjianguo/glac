package net.engining.sccc.biz.service;

import javax.annotation.Nonnull;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import net.engining.pcx.cc.param.model.enums.SysTxnCd;
import net.engining.sccc.biz.enums.AccountTradingDef;
import net.engining.sccc.biz.enums.SysInternalAcctionCdDef;

/**
 * 通过联机队长交易和期数判断入账交易代码
 * 
 * @author zhengzhaoxian
 * 
 * 
 *
 */

/// ** 贷款发放记账 */ D,
/// ** 授额提降额记账*/ S,
/// ** 费用收取记账*/ F,
/// ** 还款记账*/ H,
/// ** 退款记账*/ T;

@Service
@Validated
public class ComputingTxnCdService {
	// 内部帐交易代码可以为空
	public SysTxnCd getTxnCd(@Nonnull AccountTradingDef accountTrading, Integer totalPeriod,
			SysInternalAcctionCdDef sysInternalAcctionCdDef, Integer ageCdBefore, Boolean isUnion) {

		SysTxnCd txnCd = null;

		if (AccountTradingDef.S.equals(accountTrading)) {
			if (SysInternalAcctionCdDef.S031.equals(sysInternalAcctionCdDef)
					|| SysInternalAcctionCdDef.S032.equals(sysInternalAcctionCdDef)) {
				txnCd = SysTxnCd.T003 ;
			}
			if (SysInternalAcctionCdDef.S033.equals(sysInternalAcctionCdDef)) {
				txnCd = SysTxnCd.T004 ;
			}
		} else if(AccountTradingDef.D.equals(accountTrading)){
			if (totalPeriod <= 12) {
				if (isUnion == true) {
					txnCd = SysTxnCd.T005 ;
				}
				if (isUnion == false) {
					txnCd = SysTxnCd.T006 ;
				}
			}
			if (totalPeriod > 12) {
				if (isUnion == true) {
					txnCd = SysTxnCd.T007 ;
				}
				if (isUnion == false) {
					txnCd = SysTxnCd.T008 ;
				}
			}
		} else if(AccountTradingDef.INTEACC.equals(accountTrading)){
			if (totalPeriod <= 12) {
				if (ageCdBefore == 0) {
					txnCd = SysTxnCd.T009 ;
				}
				if (ageCdBefore == 1 || ageCdBefore == 2 || ageCdBefore == 3) {
					txnCd = SysTxnCd.T400 ;
				}
				if (ageCdBefore > 3) {
					txnCd = SysTxnCd.T010 ;
				}
			}
			if (totalPeriod > 12) {
				if (ageCdBefore == 0) {
					txnCd = SysTxnCd.T011 ;
				}
				if (ageCdBefore == 1 || ageCdBefore == 2 || ageCdBefore == 3) {
					txnCd = SysTxnCd.T401 ;
				}
				if (ageCdBefore > 3) {
					txnCd = SysTxnCd.T012 ;
				}
			}
		} else if(AccountTradingDef.PINTACC.equals(accountTrading)){
			if (totalPeriod <= 12) {
				if (ageCdBefore <= 3) {
					txnCd = SysTxnCd.T013 ;
				}
				if (ageCdBefore > 3) {
					txnCd = SysTxnCd.T014 ;
				}
			}
			if (totalPeriod > 12) {
				if (ageCdBefore <= 3) {
					txnCd = SysTxnCd.T015 ;
				}
				if (ageCdBefore > 3) {
					txnCd = SysTxnCd.T016 ;
				}
			}
		} else if(AccountTradingDef.LBAL.equals(accountTrading)){
			if (totalPeriod <= 12) {
				if (ageCdBefore == 1 || ageCdBefore == 2 || ageCdBefore == 3) {
					txnCd = SysTxnCd.T017 ;
				}
				if (ageCdBefore == 4) {
					txnCd = SysTxnCd.T018 ;
				}
				if (ageCdBefore == 5 || ageCdBefore == 6) {
					txnCd = SysTxnCd.T019 ;
				}
				if (ageCdBefore >= 7) {
					txnCd = SysTxnCd.T020 ;
				}
			}
			if (totalPeriod > 12) {
				if (ageCdBefore == 1 || ageCdBefore == 2 || ageCdBefore == 3) {
					txnCd = SysTxnCd.T021 ;
				}
				if (ageCdBefore == 4) {
					txnCd = SysTxnCd.T022 ;
				}
				if (ageCdBefore == 5 || ageCdBefore == 6) {
					txnCd = SysTxnCd.T023 ;
				}
				if (ageCdBefore >= 7) {
					txnCd = SysTxnCd.T024 ;
				}
			}
		} else if(AccountTradingDef.INTE.equals(accountTrading)){
			if (totalPeriod <= 12) {
				if (ageCdBefore <= 3) {
					txnCd = SysTxnCd.T025 ;
				}
				if (ageCdBefore > 3) {
					txnCd = SysTxnCd.T026 ;
				}
			}
			if (totalPeriod > 12) {
				if (ageCdBefore <= 3) {
					txnCd = SysTxnCd.T027 ;
				}
				if (ageCdBefore > 3) {
					txnCd = SysTxnCd.T028 ;
				}
			}
		} else if(AccountTradingDef.PINT.equals(accountTrading)){
			if (totalPeriod <= 12) {
				if (ageCdBefore == 4) {
					txnCd = SysTxnCd.T054 ;
				}
			}
			if (totalPeriod > 12) {
				if (ageCdBefore == 4) {
					txnCd = SysTxnCd.T055 ;
				}
			}
		} else if(AccountTradingDef.T.equals(accountTrading)){
			if (ageCdBefore == 0) {
				txnCd = SysTxnCd.T029 ;
			}else{
				txnCd = SysTxnCd.T030 ;
			}
		} else if(AccountTradingDef.F.equals(accountTrading)){
			txnCd = SysTxnCd.T031 ;
		} else if(AccountTradingDef.H.equals(accountTrading)){
			if (totalPeriod <= 12) {
				if (isUnion == true) {
					if (ageCdBefore == 0) {
						txnCd = SysTxnCd.T032 ;
					}
					if (ageCdBefore == 1 || ageCdBefore == 2 || ageCdBefore == 3) {
						txnCd = SysTxnCd.T033 ;
					}
					if (ageCdBefore == 4) {
						txnCd = SysTxnCd.T034 ;
					}
					if (ageCdBefore == 5 || ageCdBefore == 6) {
						txnCd = SysTxnCd.T035 ;
					}
					if (ageCdBefore == 7) {
						txnCd = SysTxnCd.T036 ;
					}
				}
				if (isUnion == false){
					if (ageCdBefore == 0) {
						txnCd = SysTxnCd.T037 ;
					}
					if (ageCdBefore == 1 || ageCdBefore == 2 ||ageCdBefore == 3) {
						txnCd = SysTxnCd.T038 ;
					}
					if (ageCdBefore == 4) {
						txnCd = SysTxnCd.T039 ;
					}
					if (ageCdBefore == 5 || ageCdBefore == 6) {
						txnCd = SysTxnCd.T040 ;
					}
					if (ageCdBefore == 7) {
						txnCd = SysTxnCd.T041 ;
					}
				}
			}
			if(totalPeriod > 12){
				if (isUnion == true) {
					if (ageCdBefore == 0) {
						txnCd = SysTxnCd.T042 ;
					}
					if (ageCdBefore == 1 || ageCdBefore == 2 || ageCdBefore == 3) {
						txnCd = SysTxnCd.T043 ;
					}
					if (ageCdBefore == 4) {
						txnCd = SysTxnCd.T044 ;
					}
					if (ageCdBefore == 5 || ageCdBefore == 6) {
						txnCd = SysTxnCd.T045 ;
					}
					if (ageCdBefore == 7) {
						txnCd = SysTxnCd.T046 ;
					}
				}
				if (isUnion == true){
					if (ageCdBefore == 0) {
						txnCd = SysTxnCd.T047 ;
					}
					if (ageCdBefore == 1 || ageCdBefore == 2 || ageCdBefore == 3) {
						txnCd = SysTxnCd.T048 ;
					}
					if (ageCdBefore == 4) {
						txnCd = SysTxnCd.T049 ;
					}
					if (ageCdBefore == 5 || ageCdBefore == 6) {
						txnCd = SysTxnCd.T050 ;
					}
					if (ageCdBefore == 7) {
						txnCd = SysTxnCd.T051 ;
					}
				}
			}
		} else if(AccountTradingDef.OTAX_INTE.equals(accountTrading)){
			if (totalPeriod <= 12) {
				if (ageCdBefore == 0) {
					txnCd = SysTxnCd.T052 ;
				}
				if (ageCdBefore == 1 || ageCdBefore == 2 || ageCdBefore == 3) {
					txnCd = SysTxnCd.T402 ;
				}
			}
			if (totalPeriod > 12) {
				if (ageCdBefore == 0) {
					txnCd = SysTxnCd.T053 ;
				}
				if (ageCdBefore == 1 || ageCdBefore == 2 || ageCdBefore == 3) {
					txnCd = SysTxnCd.T403 ;
				}
			}
		} else if(AccountTradingDef.OTAX_PINT.equals(accountTrading)){
			if (totalPeriod <= 12) {
				txnCd = SysTxnCd.T404 ;
			}
			if (totalPeriod > 12) {
				txnCd = SysTxnCd.T405 ;
			}
		} else if(AccountTradingDef.LBAL_RETURN.equals(accountTrading)){
			if (totalPeriod <= 12) {
				if (ageCdBefore == 1 || ageCdBefore == 2 || ageCdBefore == 3) {
					txnCd = SysTxnCd.T406 ;
				}
				if (ageCdBefore == 4) {
					txnCd = SysTxnCd.T407 ;
				}
				if (ageCdBefore == 5 || ageCdBefore == 6) {
					txnCd = SysTxnCd.T408 ;
				}
				if (ageCdBefore >= 7) {
					txnCd = SysTxnCd.T409 ;
				}
			}
			if (totalPeriod > 12) {
				if (ageCdBefore == 1 || ageCdBefore == 2 || ageCdBefore == 3) {
					txnCd = SysTxnCd.T410 ;
				}
				if (ageCdBefore == 4) {
					txnCd = SysTxnCd.T411 ;
				}
				if (ageCdBefore == 5 || ageCdBefore == 6) {
					txnCd = SysTxnCd.T412 ;
				}
				if (ageCdBefore >= 7) {
					txnCd = SysTxnCd.T413 ;
				}
			}
		} else if(AccountTradingDef.C_INTEACC.equals(accountTrading)){
			if (totalPeriod <= 12) {
				if (ageCdBefore == 0) {
					txnCd = SysTxnCd.T414 ;
				}
				if (ageCdBefore == 1 || ageCdBefore == 2 || ageCdBefore == 3) {
					txnCd = SysTxnCd.T415 ;
				}
				if (ageCdBefore > 3) {
					txnCd = SysTxnCd.T416 ;
				}
			}
			if (totalPeriod > 12) {
				if (ageCdBefore == 0) {
					txnCd = SysTxnCd.T417 ;
				}
				if (ageCdBefore == 1 || ageCdBefore == 2 || ageCdBefore == 3) {
					txnCd = SysTxnCd.T418 ;
				}
				if (ageCdBefore > 3) {
					txnCd = SysTxnCd.T419 ;
				}
			}
		} else if(AccountTradingDef.C_PINTACC.equals(accountTrading)){
			if (totalPeriod <= 12) {
				if (ageCdBefore <= 3) {
					txnCd = SysTxnCd.T420 ;
				}
				if (ageCdBefore > 3) {
					txnCd = SysTxnCd.T421 ;
				}
			}
			if (totalPeriod > 12) {
				if (ageCdBefore <= 3) {
					txnCd = SysTxnCd.T422 ;
				}
				if (ageCdBefore > 3) {
					txnCd = SysTxnCd.T423 ;
				}
			}
		} else if(AccountTradingDef.C_LBAL.equals(accountTrading)){
			if (totalPeriod <= 12) {
				if (ageCdBefore == 1 || ageCdBefore == 2 || ageCdBefore == 3) {
					txnCd = SysTxnCd.T424 ;
				}
				if (ageCdBefore == 4) {
					txnCd = SysTxnCd.T425 ;
				}
				if (ageCdBefore == 5 || ageCdBefore == 6) {
					txnCd = SysTxnCd.T426 ;
				}
				if (ageCdBefore >= 7) {
					txnCd = SysTxnCd.T427 ;
				}
			}
			if (totalPeriod > 12) {
				if (ageCdBefore == 1 || ageCdBefore == 2 || ageCdBefore == 3) {
					txnCd = SysTxnCd.T428 ;
				}
				if (ageCdBefore == 4) {
					txnCd = SysTxnCd.T429 ;
				}
				if (ageCdBefore == 5 || ageCdBefore == 6) {
					txnCd = SysTxnCd.T430 ;
				}
				if (ageCdBefore >= 7) {
					txnCd = SysTxnCd.T431 ;
				}
			}
		} else if(AccountTradingDef.C_INTE.equals(accountTrading)){
			if (totalPeriod <= 12) {
				if (ageCdBefore <= 3) {
					txnCd = SysTxnCd.T432 ;
				}
				if (ageCdBefore > 3) {
					txnCd = SysTxnCd.T433 ;
				}
			}
			if (totalPeriod > 12) {
				if (ageCdBefore <= 3) {
					txnCd = SysTxnCd.T434 ;
				}
				if (ageCdBefore > 3) {
					txnCd = SysTxnCd.T435 ;
				}
			}
		} else if(AccountTradingDef.C_PINT.equals(accountTrading)){
			if (totalPeriod <= 12) {
				if (ageCdBefore == 4) {
					txnCd = SysTxnCd.T436 ;
				}
			}
			if (totalPeriod > 12) {
				if (ageCdBefore == 4) {
					txnCd = SysTxnCd.T437 ;
				}
			}
		}


		return txnCd;
	}

}
