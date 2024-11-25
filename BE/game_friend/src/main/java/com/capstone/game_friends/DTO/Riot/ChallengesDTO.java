package com.capstone.game_friends.DTO.Riot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true) // 정의되지 않은 필드를 무시
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChallengesDTO {
    private int assistStreakCount;
    private int baronBuffGoldAdvantageOverThreshold;
    private float controlWardTimeCoverageInRiverOrEnemyHalf;
    private int earliestBaron;
    private int earliestDragonTakedown;
    private int earliestElderDragon;
    private int earlyLaningPhaseGoldExpAdvantage;
    private int fasterSupportQuestCompletion;
    private int fastestLegendary;
    private int hadAfkTeammate;
    private int highestChampionDamage;
    private int highestCrowdControlScore;
    private int highestWardKills;
    private int junglerKillsEarlyJungle;
    private int killsOnLanersEarlyJungleAsJungler;
    private int laningPhaseGoldExpAdvantage;
    private int legendaryCount;
    private float maxCsAdvantageOnLaneOpponent;
    private int maxLevelLeadLaneOpponent;
    private int mostWardsDestroyedOneSweeper;
    private int mythicItemUsed;
    private int playedChampSelectPosition;
    private int soloTurretsLategame;
    private int takedownsFirst25Minutes;
    private int teleportTakedowns;
    private int thirdInhibitorDestroyedTime;
    private int threeWardsOneSweeperCount;
    private float visionScoreAdvantageLaneOpponent;
    private int infernalScalePickup;
    private int fistBumpParticipation;
    private int voidMonsterKill;
    private int abilityUses;
    private int acesBefore15Minutes;
    private float alliedJungleMonsterKills;
    private int baronTakedowns;
    private int blastConeOppositeOpponentCount;
    private int bountyGold;
    private int buffsStolen;
    private int completeSupportQuestInTime;
    private int controlWardsPlaced;
    private float damagePerMinute;
    private float damageTakenOnTeamPercentage;
    private int dancedWithRiftHerald;
    private int deathsByEnemyChamps;
    private int dodgeSkillShotsSmallWindow;
    private int doubleAces;
    private int dragonTakedowns;
    private List<Integer> legendaryItemUsed;
    private float effectiveHealAndShielding;
    private int elderDragonKillsWithOpposingSoul;
    private int elderDragonMultikills;
    private int enemyChampionImmobilizations;
    private float enemyJungleMonsterKills;
    private int epicMonsterKillsNearEnemyJungler;
    private int epicMonsterKillsWithin30SecondsOfSpawn;
    private int epicMonsterSteals;
    private int epicMonsterStolenWithoutSmite;
    private int firstTurretKilled;
    private float firstTurretKilledTime;
    private int flawlessAces;
    private int fullTeamTakedown;
    private float gameLength;
    private int getTakedownsInAllLanesEarlyJungleAsLaner;
    private float goldPerMinute;
    private int hadOpenNexus;
    private int immobilizeAndKillWithAlly;
    private int initialBuffCount;
    private int initialCrabCount;
    private float jungleCsBefore10Minutes;
    private int junglerTakedownsNearDamagedEpicMonster;
    private float kda;
    private int killAfterHiddenWithAlly;
    private int killedChampTookFullTeamDamageSurvived;
    private int killingSprees;
    private float killParticipation;
    private int killsNearEnemyTurret;
    private int killsOnOtherLanesEarlyJungleAsLaner;
    private int killsOnRecentlyHealedByAramPack;
    private int killsUnderOwnTurret;
    private int killsWithHelpFromEpicMonster;
    private int knockEnemyIntoTeamAndKill;
    private int turretsDestroyedBeforePlatesFall;
    private int landSkillShotsEarlyGame;
    private int laneMinionsFirst10Minutes;
    private int lostAnInhibitor;
    private int maxKillDeficit;
    private int mejaisFullStackInTime;
    private float moreEnemyJungleThanOpponent;
    private int multiKillOneSpell;
    private int multikills;
    private int multikillsAfterAggressiveFlash;
    private int multiTurretRiftHeraldCount;
    private int outerTurretExecutesBefore10Minutes;
    private int outnumberedKills;
    private int outnumberedNexusKill;
    private int perfectDragonSoulsTaken;
    private int perfectGame;
    private int pickKillWithAlly;
    private int poroExplosions;
    private int quickCleanse;
    private int quickFirstTurret;
    private int quickSoloKills;
    private int riftHeraldTakedowns;
    private int saveAllyFromDeath;
    private int scuttleCrabKills;
    private float shortestTimeToAceFromFirstTakedown;
    private int skillshotsDodged;
    private int skillshotsHit;
    private int snowballsHit;
    private int soloBaronKills;
    private int swarmDefeatAatrox;
    private int swarmDefeatBriar;
    private int swarmDefeatMiniBosses;
    private int swarmEvolveWeapon;
    private int swarmHave3Passives;
    private int swarmKillEnemy;
    private float swarmPickupGold;
    private int swarmReachLevel50;
    private int swarmSurvive15Min;
    private int swarmWinWith5EvolvedWeapons;
    private int soloKills;
    private int stealthWardsPlaced;
    private int survivedSingleDigitHpCount;
    private int survivedThreeImmobilizesInFight;
    private int takedownOnFirstTurret;
    private int takedowns;
    private int takedownsAfterGainingLevelAdvantage;
    private int takedownsBeforeJungleMinionSpawn;
    private int takedownsFirstXMinutes;
    private int takedownsInAlcove;
    private int takedownsInEnemyFountain;
    private int teamBaronKills;
    private float teamDamagePercentage;
    private int teamElderDragonKills;
    private int teamRiftHeraldKills;
    private int tookLargeDamageSurvived;
    private int turretPlatesTaken;
    private int turretsTakenWithRiftHerald;
    private int turretTakedowns;
    private int twentyMinionsIn3SecondsCount;
    private int twoWardsOneSweeperCount;
    private int unseenRecalls;
    private float visionScorePerMinute;
    private int wardsGuarded;
    private int wardTakedowns;
    private int wardTakedownsBefore20M;
}
