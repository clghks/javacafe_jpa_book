package com.clghks.jpashop.service

import com.clghks.jpashop.domain.Member
import com.clghks.jpashop.repository.MemberRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MemberService(
    @Autowired
    private val memberRepository: MemberRepository
) {
    fun join(member: Member): Long {
        validateDuplicateMember(member)
        memberRepository.save(member)

        return member.id
    }

    private fun validateDuplicateMember(member: Member) {
        val findMembers = memberRepository.findByName(member.name)
        if (!findMembers.isEmpty()) {
            throw IllegalStateException("이미 존재하는 회원입니다.")
        }
    }

    fun findMembers(): List<Member> {
        return memberRepository.findAll()
    }

    fun findOne(memberId: Long): Member {
        return memberRepository.findOne(memberId)
    }
}